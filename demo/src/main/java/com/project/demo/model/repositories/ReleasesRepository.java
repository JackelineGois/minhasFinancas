package com.project.demo.model.repositories;

import com.project.demo.entities.Releases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {}
