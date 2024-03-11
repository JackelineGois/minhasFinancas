package com.project.demo.model.repositories;

import com.project.demo.entities.User;
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
public class UserRepositoryTest {

  @Autowired
  UserRepository repository;

  @Autowired
  TestEntityManager entityManager;

  @Test
  public void CheckEmailExistence() {
    User usuario = createUser();

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
    User usuario = createUser();

    User usuarioSalvo = repository.save(usuario);

    Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
  }

  @Test
  public void persistUserEmailDatabase() {
    User user = createUser();
    entityManager.persist(user);

    Optional<User> result = repository.findByEmail("usuario@email.com");

    Assertions.assertThat(result.isPresent()).isTrue();
  }

  @Test
  public void returnEmptyUserByEmailWhenNotInDatabase() {
    Optional<User> result = repository.findByEmail("usuario@email.com");

    Assertions.assertThat(result.isPresent()).isFalse();
  }

  public static User createUser() {
    return User
      .builder()
      .name("usuario")
      .email("usuario@email.com")
      .password("jsds")
      .build();
  }
}
