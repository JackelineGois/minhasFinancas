package com.project.demo.service;

import java.util.List;

import com.project.demo.entities.Lancamento;
import com.project.demo.model.enums.StatusLancamento;


public interface Launch {
  Lancamento salvar(Lancamento launch);
  Lancamento update(Lancamento launch);
  void deletar(Lancamento launch);
  List<Lancamento> buscar(Lancamento filterLaunch);
  void statusUpdate(Lancamento launch, StatusLancamento status);
}
