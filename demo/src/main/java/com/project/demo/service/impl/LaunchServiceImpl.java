package com.project.demo.service.impl;

import com.project.demo.entities.Lancamento;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.enums.StatusLancamento;
import com.project.demo.model.repositories.LancamentoRepositories;
import com.project.demo.service.Launch;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LaunchServiceImpl implements Launch {

  private LancamentoRepositories repository;

  public LaunchServiceImpl(LancamentoRepositories repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public Lancamento salvar(Lancamento launch) {
    validate(launch);
    launch.setStatus(StatusLancamento.PENDENTE);
    return repository.save(launch);
  }

  @Override
  @Transactional
  public Lancamento update(Lancamento launch) {
    Objects.requireNonNull(launch.getId());
    validate(launch);
    return repository.save(launch);
  }

  @Override
  @Transactional
  public void deletar(Lancamento launch) {
    Objects.requireNonNull(launch.getId());
    repository.delete(launch);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Lancamento> buscar(Lancamento filterLaunch) {
    Example example = Example.of(
      filterLaunch,
      ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(StringMatcher.CONTAINING)
    );
    return repository.findAll(example);
  }

  @Override
  public void statusUpdate(Lancamento launch, StatusLancamento status) {
    launch.setStatus(status);
    update(launch);
  }

  @Override
  public void validate(Lancamento launch) {
    if (
      launch.getDescricao() == null || launch.getDescricao().trim().equals("")
    ) {
      throw new RegraNegocioException("Enter a valid Description");
    }
    if (
      launch.getMes() == null || launch.getMes() < 1 || launch.getMes() > 12
    ) {
      throw new RegraNegocioException("Enter a valid Month");
    }
    if (launch.getAno() == null || launch.getAno().toString().length() != 4) {
      throw new RegraNegocioException("Enter a valid Year");
    }
    if (launch.getUsuario() == null || launch.getUsuario().getId() == null) {
      throw new RegraNegocioException("Inform a User");
    }
    if (
      launch.getValor() == null ||
      launch.getValor().compareTo(BigDecimal.ZERO) < 1
    ) {
      throw new RegraNegocioException("Inform a valid value");
    }
    if (launch.getTipo() == null) {
      throw new RegraNegocioException("Inform a type of launch");
    }
  }
}
