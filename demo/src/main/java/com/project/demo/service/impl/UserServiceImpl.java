package com.project.demo.service.impl;

import com.project.demo.entities.User;
import com.project.demo.exceptions.ErroAutenticacao;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.repositories.UsuarioRepositories;
import com.project.demo.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private UsuarioRepositories repository;

  @Autowired
  public UserServiceImpl(UsuarioRepositories repository) {
    this.repository = repository;
  }

  @Override
  public User autenticar(String email, String senha) {
    Optional<User> user = repository.findByEmail(email);

    if (!user.isPresent()) {
      throw new ErroAutenticacao("Usuário não encontrado");
    }
    if (!user.get().getPassword().equals(senha)) {
      throw new ErroAutenticacao("Senha inválida");
    }
    return user.get();
  }

  @Override
  @Transactional
  public User salvarUsuario(User usuario) {
    validarEmail(usuario.getEmail());
    return repository.save(usuario);
  }

  @Override
  public void validarEmail(String email) {
    boolean exist = repository.existsByEmail(email);
    if (exist) {
      throw new RegraNegocioException("Email already registered");
    }
  }

  @Override
  public Optional<User> getById(Long id) {
    return repository.findById(id);
  }
}
