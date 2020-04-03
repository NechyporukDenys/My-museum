package com.nechyporuk.museum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor

@Entity
@Table(name = "exhibition")
public class Exhibition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "creation_date")
  private Date creationDate;
  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private Author author;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "exhibition_material",
          joinColumns = @JoinColumn(name = "exhibition_id"),
          inverseJoinColumns = @JoinColumn(name = "material_id"))
  private List<Material> materials;

  @ManyToOne
  @JoinColumn(name = "hall_id")
  private Hall hall;
}
