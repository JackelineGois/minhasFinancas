package com.project.demo.model.repositories;

import com.project.demo.entities.Releases;
import com.project.demo.model.enums.ReleaseType;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {
  @Query(
    value = "select sum(l.value) from Releases l join l.user user " +
    "where user.id =:idUser and l.type=:type group by user"
  )
  BigDecimal getBalanceByTypeOfReleasesAndUsers(
    @Param("idUser") Long idUser,
    @Param("type") ReleaseType type
  );
}
