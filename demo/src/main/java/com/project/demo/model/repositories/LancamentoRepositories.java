package com.project.demo.model.repositories;

import com.project.demo.entities.Releases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepositories
  extends JpaRepository<Releases, Long> {}
