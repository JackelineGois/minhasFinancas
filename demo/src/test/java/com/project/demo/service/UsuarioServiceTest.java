package com.project.demo.service;

import com.project.demo.entities.Usuario;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.repositories.UsuarioRepositories;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

  @Autowired
  UsuarioService service;

  @Autowired
  UsuarioRepositories repository;

  @Test(expected = Test.None.class)
  public void ValidatedEmail() {
    repository.deleteAll();

    service.validarEmail("email@email.com");
  }

  @Test(expected = RegraNegocioException.class)
  public void ErrorToValidateEmailWhenThereIsRegistration() {
    Usuario usuario = Usuario
      .builder()
      .nome("usuario")
      .email("email@email.com")
      .build();
    repository.save(usuario);

    service.validarEmail("email@email.com");
  }
}
