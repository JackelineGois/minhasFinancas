package com.project.demo.model.repositories;

import com.project.demo.entities.Usuario;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

  @Autowired
  UsuarioRepositories repository;

  @Autowired
  TestEntityManager entityManager;

  @Test
  public void CheckEmailExistence() {
    Usuario usuario = createUser();

    entityManager.persist(usuario);

    boolean results = repository.existsByEmail("usuario@email.com");

    Assertions.assertThat(results).isTrue();
  }

  @Test
  public void returnFalseWhenThereIsNoRegisteredUserWithTheEmail() {
    boolean result = repository.existsByEmail("usuario@email.com");

    Assertions.assertThat(result).isFalse();
  }

  @Test
  public void persistUserDatabase() {
    Usuario usuario = createUser();

    Usuario usuarioSalvo = repository.save(usuario);

    Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
  }

  @Test
  public void persistUserEmailDatabase() {
    Usuario user = createUser();
    entityManager.persist(user);

    Optional<Usuario> result = repository.findByEmail("usuario@email.com");

    Assertions.assertThat(result.isPresent()).isTrue();
  }

  @Test
  public void returnEmptyUserByEmailWhenNotInDatabase() {
    Optional<Usuario> result = repository.findByEmail("usuario@email.com");

    Assertions.assertThat(result.isPresent()).isFalse();
  }

  public static Usuario createUser() {
    return Usuario
      .builder()
      .nome("usuario")
      .email("usuario@email.com")
      .senha("jsds")
      .build();
  }
}
