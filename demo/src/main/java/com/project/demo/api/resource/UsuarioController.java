package com.project.demo.api.resource;

import com.project.demo.api.dto.UsuarioDTO;
import com.project.demo.entities.Usuario;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

  private UsuarioService service;

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @PostMapping("/api/usuarios")
  public ResponseEntity save(@RequestBody UsuarioDTO dto) {
    Usuario usuario = Usuario
      .builder()
      .nome(dto.getNome())
      .email(dto.getEmail())
      .senha(dto.getSenha())
      .build();

    try {
      Usuario usuarioSalvo = service.salvarUsuario(usuario);
      return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
