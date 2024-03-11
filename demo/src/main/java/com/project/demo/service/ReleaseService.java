package com.project.demo.service;

import com.project.demo.entities.Releases;
import com.project.demo.model.enums.ReleaseStatus;
import java.util.List;

public interface ReleaseService {
  Releases salvar(Releases launch);
  Releases update(Releases launch);
  void deletar(Releases launch);
  List<Releases> buscar(Releases filterLaunch);
  void statusUpdate(Releases launch, ReleaseStatus status);
  void validate(Releases launch);
}
