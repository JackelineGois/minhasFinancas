package com.project.demo.service.impl;

import com.project.demo.entities.Usuario;
import com.project.demo.exceptions.ErroAutenticacao;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.repositories.UsuarioRepositories;
import com.project.demo.service.UsuarioService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioImpl implements UsuarioService {

  private UsuarioRepositories repository;

  @Autowired
  public UsuarioImpl(UsuarioRepositories repository) {
    this.repository = repository;
  }

  @Override
  public Usuario autenticar(String email, String senha) {
    Optional<Usuario> user = repository.findByEmail(email);

    if (!user.isPresent()) {
      throw new ErroAutenticacao("Usuário não encontrado");
    }
    if (!user.get().getSenha().equals(senha)) {
      throw new ErroAutenticacao("Senha inválida");
    }
    return user.get();
  }

  @Override
  @Transactional
  public Usuario salvarUsuario(Usuario usuario) {
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
}
