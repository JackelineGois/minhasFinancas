package com.project.demo.model.repositories;

import com.project.demo.entities.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositories extends JpaRepository<Usuario, Long> {
  boolean existsByEmail(String email);

  Optional<Usuario> findByEmail(String email);
}
