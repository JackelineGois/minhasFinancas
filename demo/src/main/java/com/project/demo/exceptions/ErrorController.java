package com.project.demo.exceptions;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController
  implements org.springframework.boot.web.servlet.error.ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    // Obtenha o código de status do erro
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());

      // Lógica para lidar com diferentes códigos de erro
      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        return "error-404"; // Página de erro 404 personalizada
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        return "error-500"; // Página de erro 500 personalizada
      }
    }
    return "error"; // Página de erro genérica
  }
}
