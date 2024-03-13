package com.project.demo.service;

import com.project.demo.entities.User;
import java.util.Optional;

public interface UserService {
  User autenticar(String email, String password);

  User saveUser(User usuario);

  void validarEmail(String email);

  Optional<User> getById(Long id);
}
