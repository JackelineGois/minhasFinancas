package com.project.demo.api.resource;

import com.project.demo.api.dto.ReleasesDTO;
import com.project.demo.entities.Releases;
import com.project.demo.entities.User;
import com.project.demo.exceptions.RegraNegocioException;
import com.project.demo.model.enums.ReleaseStatus;
import com.project.demo.model.enums.ReleaseType;
import com.project.demo.service.ReleaseService;
import com.project.demo.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/releases")
@RequiredArgsConstructor
public class ReleasesController {

  private final ReleaseService service;
  private final UserService userService;

  @PostMapping
  public ResponseEntity save(@RequestBody ReleasesDTO dto) {
    try {
      Releases entity = convert(dto);
      entity = service.save(entity);
      return new ResponseEntity(entity, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity update(
    @PathVariable("id") Long id,
    @RequestBody ReleasesDTO dto
  ) {
    return service
      .obtainByID(id)
      .map(entity -> {
        try {
          Releases releases = convert(dto);
          releases.setId(entity.getId());
          service.update(releases);
          return ResponseEntity.ok(releases);
        } catch (RegraNegocioException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
        }
      })
      .orElseGet(() ->
        new ResponseEntity(
          "Releases not found in the database",
          HttpStatus.BAD_REQUEST
        )
      );
  }

  @DeleteMapping("{id}")
  public ResponseEntity delete(@PathVariable("id") Long id) {
    return service
      .obtainByID(id)
      .map(entity -> {
        service.delete(entity);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
      })
      .orElseGet(() ->
        new ResponseEntity(
          "Releases not Found in the database",
          HttpStatus.BAD_REQUEST
        )
      );
  }

  @GetMapping
  public ResponseEntity search(
    @RequestParam(value = "description", required = false) String description,
    @RequestParam(value = "month", required = false) Integer month,
    @RequestParam(value = "year", required = false) Integer year,
    @RequestParam(value = "user") Long idUser
  ) {
    Releases releasesFilter = new Releases();

    releasesFilter.setDescription(description);
    releasesFilter.setMonth(month);
    releasesFilter.setYear(year);

    Optional<User> user = userService.getById(idUser);
    if (!user.isPresent()) {
      return ResponseEntity
        .badRequest()
        .body("It was not possible to carry out the query. User not found");
    } else {
      releasesFilter.setUser(user.get());
    }

    List<Releases> releases = service.search(releasesFilter);
    return ResponseEntity.ok(releases);
  }

  private Releases convert(ReleasesDTO dto) {
    Releases releases = new Releases();

    releases.setId(dto.getId());
    releases.setDescription(dto.getDescription());
    releases.setYear(dto.getYear());
    releases.setMonth(dto.getMonth());
    releases.setValue(dto.getValue());

    User user = userService
      .getById(dto.getUser())
      .orElseThrow(() ->
        new RegraNegocioException("User not found for the id provided")
      );
    releases.setUser(user);

    if (dto.getType() != null) {
      releases.setType(ReleaseType.valueOf(dto.getType()));
    }
    if (dto.getStatus() != null) {
      releases.setStatus(ReleaseStatus.valueOf(dto.getStatus()));
    }

    return releases;
  }
}
