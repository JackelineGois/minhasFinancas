package com.project.demo.service;

import java.util.Optional;

import com.project.demo.entities.User;

public interface UserService {
  User autenticar(String email, String senha);

  User salvarUsuario(User usuario);

  void validarEmail(String email);

  Optional<User> getById(Long id);
}
