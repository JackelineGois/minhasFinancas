package com.project.demo.service;

import com.project.demo.entities.Lancamento;
import com.project.demo.model.enums.StatusLancamento;
import java.util.List;

public interface Launch {
  Lancamento salvar(Lancamento launch);
  Lancamento update(Lancamento launch);
  void deletar(Lancamento launch);
  List<Lancamento> buscar(Lancamento filterLaunch);
  void statusUpdate(Lancamento launch, StatusLancamento status);
  void validate(Lancamento launch);
}
