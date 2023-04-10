package com.interview.planningpoker.v1.dto;

import com.interview.planningpoker.domain.enumeration.UserStoriesStatusEnum;
import com.interview.planningpoker.domain.model.Session;
import com.interview.planningpoker.domain.model.User;
import com.interview.planningpoker.domain.model.UserStory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserStoryCreateDTO {
  private Long id;
  private Long creatorId;
  private String name;
  private String description;
  private Long sessionId;
  private List<VoteDTO> votes = new ArrayList<>();
  
  public UserStory toEntity(Session session) {
    User user = session.getUsers().stream()
            .filter(u -> u.getId().equals(id)).findFirst().orElse(session.getCreator());
    return new UserStory(id, user, name, description, UserStoriesStatusEnum.PENDING, session, null);
  }
}
