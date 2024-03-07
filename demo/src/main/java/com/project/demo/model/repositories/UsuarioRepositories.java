package com.project.demo.model.repositories;

import com.project.demo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositories extends JpaRepository<Usuario, Long> {
  boolean existsByEmail(String email);
}
