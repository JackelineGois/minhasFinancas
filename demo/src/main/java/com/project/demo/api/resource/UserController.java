package com.project.demo.api.resource;

import com.project.demo.api.dto.UserDTO;
import com.project.demo.entities.User;
import com.project.demo.exceptions.ErroAutenticacao;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.service.ReleaseService;
import com.project.demo.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;
  private final ReleaseService releaseService;

  @PostMapping("/autenticar")
  public ResponseEntity<?> autenticar(@RequestBody UserDTO dto) {
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

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestBody UserDTO dto) {
    User usuario = User
      .builder()
      .name(dto.getName())
      .email(dto.getEmail())
      .password(dto.getPassword())
      .build();

    try {
      User userSalvo = service.saveUser(usuario);
      return new ResponseEntity<>(userSalvo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("{id}/balance")
  public ResponseEntity<?> getBalance(@PathVariable("id") Long id) {
    Optional<User> user = service.getById(id);
    if (!user.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    BigDecimal balance = releaseService.getBalanceByUser(id);
    return ResponseEntity.ok(balance);
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllUsers() {
    List<User> user = service.allUsers(null);
    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
    } else {
      return ResponseEntity.ok(user);
    }
  }
}
