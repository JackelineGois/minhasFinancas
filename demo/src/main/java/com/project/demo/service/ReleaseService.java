package com.project.demo.service;

import com.project.demo.entities.Releases;
import com.project.demo.model.enums.ReleaseStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ReleaseService {
  Releases save(Releases releases);
  Releases update(Releases releases);
  void delete(Releases releases);
  List<Releases> search(Releases filterReleases);
  void statusUpdate(Releases releases, ReleaseStatus status);
  void validate(Releases releases);
  Optional<Releases> obtainByID(Long id);
  BigDecimal getBalanceByUser(Long id);
}
