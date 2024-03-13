package com.project.demo.service;

import com.project.demo.entities.Releases;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.enums.ReleaseStatus;
import com.project.demo.model.repositories.ReleasesRepository;
import com.project.demo.model.repositories.ReleasesRepositoryTest;
import com.project.demo.service.impl.ReleaseServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ReleasesServiceTest {

  @SpyBean
  ReleaseServiceImpl service;

  @MockBean
  ReleasesRepository repository;

  @Test
  public void mustSaveReleases() {
    Releases releasesToSave = ReleasesRepositoryTest.createReleases();
    Mockito.doNothing().when(service).validate(releasesToSave);

    Releases releasesSaved = ReleasesRepositoryTest.createReleases();
    releasesSaved.setId(1l);
    releasesSaved.setStatus(ReleaseStatus.PENDENTE);
    Mockito.when(repository.save(releasesToSave)).thenReturn(releasesSaved);

    Releases releases = service.save(releasesToSave);

    Assertions.assertThat(releases.getId()).isEqualTo(releasesSaved.getId());
    Assertions
      .assertThat((releases.getStatus()))
      .isEqualTo(ReleaseStatus.PENDENTE);
  }

  @Test
  public void shouldNotSaveReleasesWithValidateError() {
    Releases releasesToSave = ReleasesRepositoryTest.createReleases();
    Mockito
      .doThrow(RegraNegocioException.class)
      .when(service)
      .validate(releasesToSave);

    Assertions.catchThrowableOfType(
      () -> service.save(releasesToSave),
      RegraNegocioException.class
    );

    Mockito.verify(repository, Mockito.never()).save(releasesToSave);
  }

  @Test
  public void mustUpdateReleases() {
    Releases releasesUpdate = ReleasesRepositoryTest.createReleases();
    releasesUpdate.setId(1l);
    releasesUpdate.setStatus(ReleaseStatus.PENDENTE);

    Mockito.doNothing().when(service).validate(releasesUpdate);
    Mockito.when(repository.save(releasesUpdate)).thenReturn(releasesUpdate);

    service.update(releasesUpdate);

    Mockito.verify(repository, Mockito.times(1)).save(releasesUpdate);
  }

  @Test
  public void mustThrowAErrorIfTryReleasesUpdateNotSaved() {
    Releases releasesToUpdate = ReleasesRepositoryTest.createReleases();

    Assertions.catchThrowableOfType(
      () -> service.update(releasesToUpdate),
      NullPointerException.class
    );

    Mockito.verify(repository, Mockito.never()).save(releasesToUpdate);
  }

  @Test
  public void mustDeleteUser() {
    Releases releases = ReleasesRepositoryTest.createReleases();
    releases.setId(1l);

    service.delete(releases);

    Mockito.verify(repository).delete(releases);
  }

  @Test
  public void mustThrowAErrorIfTryDeleteReleasesNotSaved() {
    Releases releases = ReleasesRepositoryTest.createReleases();

    Assertions.catchThrowableOfType(
      () -> service.delete(releases),
      NullPointerException.class
    );

    Mockito.verify(repository, Mockito.never()).delete(releases);
  }

  @Test
  public void mustFilterReleases() {
    Releases releases = ReleasesRepositoryTest.createReleases();
    releases.setId(1l);

    List<Releases> list = Arrays.asList(releases);
    Mockito
      .when(repository.findAll(Mockito.any(Example.class)))
      .thenReturn(list);

    List<Releases> result = service.search(releases);

    Assertions.assertThat(result).isNotEmpty().hasSize(1).contains(releases);
  }
}
