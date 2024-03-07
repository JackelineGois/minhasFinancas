package com.project.demo.service.impl;

import com.project.demo.entities.Usuario;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.repositories.UsuarioRepositories;
import com.project.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioImpl implements UsuarioService {

  private UsuarioRepositories repository;

  @Autowired
  public UsuarioImpl(UsuarioRepositories repository) {
    this.repository = repository;
  }

  @Override
  public Usuario autenticar(String email, String senha) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'autenticar'"
    );
  }

  @Override
  public Usuario salvarUsuario(Usuario usuario) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'salvarUsuario'"
    );
  }

  @Override
  public void validarEmail(String email) {
    boolean exist = repository.existsByEmail(email);
    if (exist) {
      throw new RegraNegocioException(
        "Email já cadastrado, Por favor tente novamente"
      );
    }
  }
}
