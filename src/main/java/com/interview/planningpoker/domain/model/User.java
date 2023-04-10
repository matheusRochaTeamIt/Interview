package com.interview.planningpoker.domain.model;

import com.interview.planningpoker.v1.dto.UserDTO;
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
public class User implements Serializable {
  
  @Id
  @GeneratedValue(strategy = IDENTITY)
  Long id;
  @Column
  String nickname;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "session_id", nullable = false)
  private Session session;
  
  @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
  private List<UserStory> userStories = new ArrayList<>();
  
  public User(UserDTO dto) {
    id = dto.getId();
    nickname = dto.getNickName();
  }
  
  public User(UserDTO dto, Session s) {
    id = dto.getId();
    nickname = dto.getNickName();
    session = s;
  }
}
