package com.apigateway.entities.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
