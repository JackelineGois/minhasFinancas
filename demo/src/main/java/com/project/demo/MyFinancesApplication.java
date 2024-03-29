package com.project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MyFinancesApplication implements WebMvcConfigurer {

  public void addCorsMappings(CorsRegistry registry) {
    registry
      .addMapping("/**")
      .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS");
  }

  public static void main(String[] args) {
    SpringApplication.run(MyFinancesApplication.class, args);
  }
}
