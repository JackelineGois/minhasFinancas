package com.project.demo.model.repositories;

import static org.assertj.core.api.Assertions.*;

import com.project.demo.entities.Releases;
import com.project.demo.model.enums.ReleaseStatus;
import com.project.demo.model.enums.ReleaseType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ReleasesRepositoryTest {

  @Autowired
  ReleasesRepository repository;

  @Autowired
  TestEntityManager entityManager;

  @Test
  public void mustSaveUser() {
    Releases releases = createReleases();

    releases = repository.save(releases);

    assertThat(releases.getId()).isNotNull();
  }

  @Test
  public void mustDeleteUser() {
    Releases releases = mustPersistReleases();

    releases = entityManager.find(Releases.class, releases.getId());
    repository.delete(releases);

    Releases noExistentReleases = entityManager.find(
      Releases.class,
      releases.getId()
    );

    assertThat(noExistentReleases).isNull();
  }

  @Test
  public void mustUpdateReleases() {
    Releases releases = mustPersistReleases();

    releases.setYear(2023);
    releases.setDescription("HEloe");
    releases.setType(ReleaseType.DESPESA);

    repository.save(releases);

    Releases updatedReleases = entityManager.find(
      Releases.class,
      releases.getId()
    );

    assertThat(updatedReleases.getYear()).isEqualTo(2023);
    assertThat(updatedReleases.getDescription()).isEqualTo("HEloe");

    assertThat(updatedReleases.getType()).isEqualTo(ReleaseType.DESPESA);
  }

  @Test
  public void mustSearchReleasesById() {
    Releases releases = mustPersistReleases();

    releases = entityManager.find(Releases.class, releases.getId());
    Optional<Releases> searchedReleases = repository.findById(releases.getId());

    assertThat(searchedReleases.isPresent()).isTrue();
  }

  private Releases createReleases() {
    return Releases
      .builder()
      .month(1)
      .year(2024)
      .description("Lancamento qualquer")
      .value(BigDecimal.valueOf(10))
      .type(ReleaseType.RECEITA)
      .status(ReleaseStatus.CANCELADO)
      .dateRegister(LocalDate.now())
      .build();
  }

  private Releases mustPersistReleases() {
    Releases releases = createReleases();
    entityManager.persist(releases);
    return releases;
  }
}
