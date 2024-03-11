package com.project.demo.entities;

import com.project.demo.model.enums.ReleaseStatus;
import com.project.demo.model.enums.ReleaseType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Entity
@Table(name = "releases", schema = "finance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Releases {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "description")
  private String description;

  @Column(name = "month")
  private Integer month;

  @Column(name = "year")
  private Integer year;

  @Column(name = "value")
  private BigDecimal value;

  @Column(name = "date_register")
  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
  private LocalDate dateRegister;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;

  @Column(name = "type")
  @Enumerated(value = EnumType.STRING)
  private ReleaseType type;

  @Column(name = "status")
  @Enumerated(value = EnumType.STRING)
  private ReleaseStatus status;
}
