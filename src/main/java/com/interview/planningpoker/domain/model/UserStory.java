package com.interview.planningpoker.domain.model;

import com.interview.planningpoker.domain.enumeration.UserStoriesStatusEnum;
import com.interview.planningpoker.v1.dto.UserStoryDTO;
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
public class UserStory implements Serializable {
  
  @Id
  @Column(name = "id", unique = true)
  @GeneratedValue(strategy = IDENTITY)
  Long id;
  @JoinColumn(name = "creator_id")
  @ManyToOne
  User creator;
  @Column
  String name;
  @Column
  String description;
  @Column
  UserStoriesStatusEnum status;
  
  @ManyToOne
  @JoinColumn(name = "session_id", nullable = false)
  private Session session;
  
  @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Vote> votes = new ArrayList<>();
  
  public void updateData(UserStoryDTO dto) {
    if (!description.equals(dto.getDescription()) || !name.equals(dto.getName())) {
      votes.clear();
    }
    description = dto.getDescription();
    name = dto.getName();
    status = dto.getStatus();
    
  }
}
