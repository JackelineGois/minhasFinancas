package com.project.demo.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.api.dto.UserDTO;
import com.project.demo.entities.User;
import com.project.demo.exceptions.ErroAutenticacao;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.service.ReleaseService;
import com.project.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserResourceTest {

  static final String API = "/api/usuarios";
  static final MediaType JSON = MediaType.APPLICATION_JSON;

  @Autowired
  MockMvc mvc;

  @MockBean
  UserService service;

  @MockBean
  ReleaseService releaseService;

  @Test
  public void mustAuthenticateUser() throws Exception {
    String email = "usuario@gmail.com";
    String password = "123";

    UserDTO dto = UserDTO.builder().email(email).password(password).build();
    User user = User.builder().id(1l).email(email).password(password).build();

    Mockito.when(service.autenticar(email, password)).thenReturn(user);

    String json = new ObjectMapper().writeValueAsString(dto);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .post(API.concat("/autenticar"))
      .accept(JSON)
      .contentType(JSON)
      .content(json);

    mvc
      .perform(request)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("id").value(user.getId()))
      .andExpect(MockMvcResultMatchers.jsonPath("name").value(user.getName()))
      .andExpect(
        MockMvcResultMatchers.jsonPath("email").value(user.getEmail())
      );
  }

  @Test
  public void mustReturnBadRequestToAuthenticateError() throws Exception {
    String email = "usuario@gmail.com";
    String password = "123";

    UserDTO dto = UserDTO.builder().email(email).password(password).build();

    Mockito
      .when(service.autenticar(email, password))
      .thenThrow(ErroAutenticacao.class);

    String json = new ObjectMapper().writeValueAsString(dto);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .post(API.concat("/autenticar"))
      .accept(JSON)
      .contentType(JSON)
      .content(json);

    mvc
      .perform(request)
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void mustCreateANewUser() throws Exception {
    String email = "usuario@gmail.com";
    String password = "123";

    UserDTO dto = UserDTO.builder().email(email).password(password).build();
    User user = User.builder().id(1l).email(email).password(password).build();

    Mockito.when(service.saveUser(Mockito.any(User.class))).thenReturn(user);
    String json = new ObjectMapper().writeValueAsString(dto);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .post(API.concat("/save"))
      .accept(JSON)
      .contentType(JSON)
      .content(json);

    mvc
      .perform(request)
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("id").value(user.getId()))
      .andExpect(MockMvcResultMatchers.jsonPath("name").value(user.getName()))
      .andExpect(
        MockMvcResultMatchers.jsonPath("email").value(user.getEmail())
      );
  }

  @Test
  public void mustThrowBadRequestToCreateAInvalidUser() throws Exception {
    String email = "usuario@gmail.com";
    String password = "123";

    UserDTO dto = UserDTO.builder().email(email).password(password).build();
    User user = User.builder().id(1l).email(email).password(password).build();

    Mockito
      .when(service.saveUser(Mockito.any(User.class)))
      .thenThrow(RegraNegocioException.class);
    String json = new ObjectMapper().writeValueAsString(dto);

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .post(API.concat("/save"))
      .accept(JSON)
      .contentType(JSON)
      .content(json);

    mvc
      .perform(request)
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
