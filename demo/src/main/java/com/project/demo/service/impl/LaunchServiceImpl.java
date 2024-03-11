package com.project.demo.service.impl;

import com.project.demo.entities.Lancamento;
import com.project.demo.model.enums.StatusLancamento;
import com.project.demo.model.repositories.LancamentoRepositories;
import com.project.demo.service.Launch;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LaunchServiceImpl implements Launch {

  private LancamentoRepositories repository;

  public LaunchServiceImpl(LancamentoRepositories repository) {
    this.repository = repository;
  }

  @Override
  public Lancamento salvar(Lancamento launch) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'salvar'");
  }

  @Override
  public Lancamento update(Lancamento launch) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void deletar(Lancamento launch) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deletar'");
  }

  @Override
  public List<Lancamento> buscar(Lancamento filterLaunch) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'buscar'");
  }

  @Override
  public void statusUpdate(Lancamento launch, StatusLancamento status) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'statusUpdate'"
    );
  }
}
