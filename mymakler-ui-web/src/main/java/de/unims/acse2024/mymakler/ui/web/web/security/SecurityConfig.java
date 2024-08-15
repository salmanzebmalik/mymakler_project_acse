package de.unims.acse2024.mymakler.ui.web.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configure Spring security.
 * Also see https://spring.io/guides/gs/securing-web/
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        // Configure HTTP request authorization
        .authorizeHttpRequests(auth -> auth
            // Allow public requests related to registering and creating a user
            .requestMatchers(HttpMethod.GET, "/", "/js/**", "/css/**", "/users/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
            // Also possible: requestMatchers(...).hasRole("...")

            // Disallow any other request, except if authenticated
            .anyRequest().authenticated()
        )

        // Configure login
        .formLogin(form -> form
            // These path trigger a login
            .loginPage("/users/login")
            .loginProcessingUrl("/users/login")

            // Send to profile after logging in
            .defaultSuccessUrl("/users/profile")
            .failureUrl("/users/login?error")
            .permitAll()
        )

        // Configure logout
        .logout(logout -> logout
            // This path triggers a logout
            .logoutUrl("/users/logout")

            // Delete cookies upon logout
            .deleteCookies("JSESSIONID")

            // Direct to login page with the logout-parameter set (will trigger the success-alert)
            .logoutSuccessUrl("/users/login?logout")
            .permitAll()
        )

        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider result = new DaoAuthenticationProvider();
    result.setUserDetailsService(userDetailsService);
    result.setPasswordEncoder(passwordEncoder());
    return result;
  }
}
