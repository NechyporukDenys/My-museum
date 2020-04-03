package com.nechyporuk.museum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor

@Entity
@Table(name = "material")
public class Material {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  @NotBlank
  private String name;

  @ManyToMany(mappedBy = "materials")
  @JsonIgnore
  private List<Exhibition> exhibitions;
}
