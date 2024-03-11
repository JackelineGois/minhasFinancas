package com.project.demo.api.resource;

import com.project.demo.api.dto.UserDTO;
import com.project.demo.entities.User;
import com.project.demo.exceptions.ErroAutenticacao;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @PostMapping("/autenticar")
  public ResponseEntity autenticar(@RequestBody UserDTO dto) {
    try {
      User usuarioAutenticado = service.autenticar(
        dto.getEmail(),
        dto.getPassword()
      );
      return ResponseEntity.ok(usuarioAutenticado);
    } catch (ErroAutenticacao e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity save(@RequestBody UserDTO dto) {
    User usuario = User
      .builder()
      .name(dto.getName())
      .email(dto.getEmail())
      .password(dto.getPassword())
      .build();

    try {
      User usuarioSalvo = service.salvarUsuario(usuario);
      return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
