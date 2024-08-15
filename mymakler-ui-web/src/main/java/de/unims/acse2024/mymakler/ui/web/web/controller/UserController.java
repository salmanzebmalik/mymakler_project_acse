package de.unims.acse2024.mymakler.ui.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import de.unims.acse2024.mymakler.ui.web.service.UserService;
import de.unims.acse2024.mymakler.ui.web.service.model.User;
import de.unims.acse2024.mymakler.ui.web.service.exception.ConfirmationFailed;
import de.unims.acse2024.mymakler.ui.web.service.exception.PasswordNotLongEnough;
import de.unims.acse2024.mymakler.ui.web.service.exception.UsernameTaken;

@Controller
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String login() {
    return "users/login"; // Name of the HTML document which is used. See src/main/resources/templates/users/login.html
  }

  @GetMapping("/profile")
  public String profile(@AuthenticationPrincipal User currentUser, Model model) {
    // Note that the AuthenticationPrincipal does not necessarily equal to the database entry.
    currentUser = userService.get(currentUser.getId());
    // Translate current user to UserDto to add to view.
    model.addAttribute("user", currentUser);
    // Then, display details in users/details
    return "users/profile";
  }

  @GetMapping("/edit")
  public String edit(@AuthenticationPrincipal User currentUser, Model model) {
    currentUser = userService.get(currentUser.getId());
    model.addAttribute("user", currentUser);
    return "users/edit";
  }

  @GetMapping("/register")
  public String register(@AuthenticationPrincipal User currentUser, Model model) {
    if (currentUser != null) {
      // Is already logged in. We will not register a new one.
      return "redirect:/";
    }
    // This "newUser"-object can be filled by the user in the users/register.html
    model.addAttribute("newUser", new User());
    // Return view where the attributes of the UserDto should be set
    return "users/register";
  }

  @PostMapping("/create")
  public String create(
      // This way we can retrieve the "newUser" without explicitly extracting it from the Model object.
      @ModelAttribute("newUser") @Valid User userDto, BindingResult bindingResult, HttpSession session) {
    // Check whether jakarta.validation.constraints were violated (see UserDto)
    if (bindingResult.hasErrors()) {
      return "users/register";
    }
    User user;
    try {
      user = userService.create(userDto);
    } catch (UsernameTaken e) {
      // Add error
      bindingResult.rejectValue("username", "error.user", "Username already exists");
      // Return to the users/register.html view where the error will be displayed.
      return "users/register";
    } catch (PasswordNotLongEnough e) {
      // Add error
      bindingResult.rejectValue("plaintextPassword", "error.user", "Password is too short");
      // Return to the users/register.html view where the error will be displayed.
      return "users/register";
    } catch (Exception e) {
      // General error
      bindingResult.addError(new ObjectError("user", "Registration failed"));
      return "users/register";
    }

    // Automatically log in new user
    userService.login(user);
    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        SecurityContextHolder.getContext());
    return "redirect:/"; // We redirect to execute the logic of the controller treating the "/" mapping
  }

  @PostMapping("/update")
  public String update(@AuthenticationPrincipal User currentUser, @ModelAttribute("user") @Valid User user,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "users/edit";
    }
    user.setId(currentUser.getId());
    try {
      userService.update(user);
    } catch (PasswordNotLongEnough e) {
      bindingResult.rejectValue("plaintextPassword", "error.user", "Password is too short");
      return "users/edit";
    } catch (ConfirmationFailed e) {
      bindingResult.rejectValue("confirmationPassword", "error.user", "Entered password is not valid");
      return "users/edit";
    } catch (Exception e) {
      // General error
      bindingResult.addError(new ObjectError("user", "Update of details failed"));
      return "users/edit";
    }
    return "redirect:/";
  }
}
