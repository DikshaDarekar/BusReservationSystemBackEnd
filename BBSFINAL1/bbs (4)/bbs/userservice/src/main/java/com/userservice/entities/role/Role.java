package com.userservice.entities.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="role")
@Setter
@Getter
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private short id;

  @Column(name="name",length = 25,unique = true,nullable = false)
  private String name;

}
