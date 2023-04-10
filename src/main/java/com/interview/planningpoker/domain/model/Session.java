package com.interview.planningpoker.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Session implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true)
  Long id;
  @JoinColumn(name = "creator_id")
  @ManyToOne(cascade = CascadeType.ALL)
  User creator;
  
  @OneToMany(targetEntity = User.class, mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
  List<User> users = new ArrayList<>();
  
  
  @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
  List<UserStory> stories = new ArrayList<>();
  
}
