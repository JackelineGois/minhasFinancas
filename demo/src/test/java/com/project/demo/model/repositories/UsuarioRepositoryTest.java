package com.project.demo.model.repositories;

import com.project.demo.entities.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

  @Autowired
  UsuarioRepositories repository;

  @Test
  public void CheckEmailExistence() {
    Usuario usuario = Usuario
      .builder()
      .nome("usuario")
      .email("usuario@email.com")
      .build();
    repository.save(usuario);

    boolean results = repository.existsByEmail("usuario@email.com");

    Assertions.assertThat(results).isTrue();
  }

  @Test
  public void returnFalseWhenThereIsNoRegisteredUserWithTheEmail() {
    repository.deleteAll();

    boolean result = repository.existsByEmail("usuario@email.com");

    Assertions.assertThat(result).isFalse();
  }
}
