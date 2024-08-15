package de.unims.acse2024.mymakler.svc.api.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ErrorDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ErrorRestController implements ErrorController {
  @RequestMapping("/error")
  public ErrorDto handleError(HttpServletRequest request) {
    String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

    try {
      Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
      if (status != null) {
        int statusCode = Integer.parseInt(status.toString());
        message = HttpStatus.valueOf(statusCode).getReasonPhrase();
      }
    } catch (Exception ignore) {
    }

    return new ErrorDto(message);
  }
}
