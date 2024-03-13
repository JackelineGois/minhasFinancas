package com.project.demo.service;

import com.project.demo.entities.User;
import com.project.demo.exceptions.ErroAutenticacao;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.repositories.UserRepository;
import com.project.demo.service.impl.UserServiceImpl;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

  @SpyBean
  UserServiceImpl service;

  @MockBean
  UserRepository repository;

  @Test(expected = Test.None.class)
  public void ValidatedEmail() {
    Mockito
      .when(repository.existsByEmail(Mockito.anyString()))
      .thenReturn(false);

    service.validarEmail("email@email.com");
  }

  @Test(expected = RegraNegocioException.class)
  public void ErrorToValidateEmailWhenThereIsRegistration() {
    Mockito
      .when(repository.existsByEmail(Mockito.anyString()))
      .thenReturn(true);

    service.validarEmail("email@email.com");
  }

  @Test(expected = Test.None.class)
  public void successfullyAuthenticateUser() {
    String email = "email@email.com";
    String password = "password";

    User user = User.builder().email(email).password(password).id(1l).build();

    Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

    User result = service.autenticar(email, password);

    Assertions.assertThat(result).isNotNull();
  }

  @Test
  public void mustThrowAnErrorWhenNoFindRegisteredUserWithEmailProvided() {
    Mockito
      .when(repository.findByEmail(Mockito.anyString()))
      .thenReturn(Optional.empty());

    Throwable exception = Assertions.catchThrowable(() ->
      service.autenticar("email@email.com", "password")
    );
    Assertions
      .assertThat(exception)
      .isInstanceOf(ErroAutenticacao.class)
      .hasMessage("User not found");
  }

  @Test
  public void incorrectPasswordError() {
    String password = "password";
    User user = User
      .builder()
      .email("email@email.com")
      .password(password)
      .build();
    Mockito
      .when(repository.findByEmail(Mockito.anyString()))
      .thenReturn(Optional.of(user));

    Throwable exception = Assertions.catchThrowable(() ->
      service.autenticar("email@email.com", "1223")
    );
    Assertions
      .assertThat(exception)
      .isInstanceOf(ErroAutenticacao.class)
      .hasMessage("Incorrect Password");
  }

  @Test
  public void saveUser() {
    Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
    User user = User
      .builder()
      .id(1l)
      .name("name")
      .email("email@email.com")
      .password("password")
      .build();
    Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

    User usuarioSalvo = service.saveUser(new User());

    Assertions.assertThat(usuarioSalvo).isNotNull();
    Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
    Assertions.assertThat(usuarioSalvo.getName()).isEqualTo("name");
    Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
    Assertions.assertThat(usuarioSalvo.getPassword()).isEqualTo("password");
  }

  @Test(expected = RegraNegocioException.class)
  public void dontSaveUserWithAnAlreadyRegisteredEmail() {
    String email = "email@email.com";
    User user = User.builder().email(email).build();
    Mockito
      .doThrow(RegraNegocioException.class)
      .when(service)
      .validarEmail(email);

    service.saveUser(user);

    Mockito.verify(repository, Mockito.never()).save(user);
  }
}
