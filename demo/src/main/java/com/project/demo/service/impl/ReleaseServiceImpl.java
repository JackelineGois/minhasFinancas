package com.project.demo.service.impl;

import com.project.demo.entities.Releases;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.enums.ReleaseStatus;
import com.project.demo.model.repositories.LancamentoRepositories;
import com.project.demo.service.ReleaseService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReleaseServiceImpl implements ReleaseService {

  private LancamentoRepositories repository;

  public ReleaseServiceImpl(LancamentoRepositories repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public Releases salvar(Releases launch) {
    validate(launch);
    launch.setStatus(ReleaseStatus.PENDENTE);
    return repository.save(launch);
  }

  @Override
  @Transactional
  public Releases update(Releases launch) {
    Objects.requireNonNull(launch.getId());
    validate(launch);
    return repository.save(launch);
  }

  @Override
  @Transactional
  public void deletar(Releases launch) {
    Objects.requireNonNull(launch.getId());
    repository.delete(launch);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Releases> buscar(Releases filterLaunch) {
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
  public void statusUpdate(Releases launch, ReleaseStatus status) {
    launch.setStatus(status);
    update(launch);
  }

  @Override
  public void validate(Releases launch) {
    if (
      launch.getDescription() == null ||
      launch.getDescription().trim().equals("")
    ) {
      throw new RegraNegocioException("Enter a valid Description");
    }
    if (
      launch.getMonth() == null ||
      launch.getMonth() < 1 ||
      launch.getMonth() > 12
    ) {
      throw new RegraNegocioException("Enter a valid Month");
    }
    if (
      launch.getYear() == null || launch.getMonth().toString().length() != 4
    ) {
      throw new RegraNegocioException("Enter a valid Year");
    }
    if (launch.getUser() == null || launch.getUser().getId() == null) {
      throw new RegraNegocioException("Inform a User");
    }
    if (
      launch.getValue() == null ||
      launch.getValue().compareTo(BigDecimal.ZERO) < 1
    ) {
      throw new RegraNegocioException("Inform a valid value");
    }
    if (launch.getType() == null) {
      throw new RegraNegocioException("Inform a type of launch");
    }
  }
}