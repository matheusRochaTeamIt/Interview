package com.interview.planningpoker.v1.dto;

import com.interview.planningpoker.domain.enumeration.UserStoriesStatusEnum;
import com.interview.planningpoker.domain.model.UserStory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserStoryDTO {
  private Long id;
  private UserDTO creator;
  private String name;
  private String description;
  private UserStoriesStatusEnum status;
  private List<VoteDTO> votes = new ArrayList<>();
  
  public UserStoryDTO(UserStory ent) {
    id = ent.getId();
    this.creator = new UserDTO(ent.getCreator());
    name = ent.getName();
    description = ent.getDescription();
    status = ent.getStatus();
    if (Objects.nonNull(ent.getVotes())) {
      votes.addAll(ent.getVotes().stream().map(VoteDTO::new).collect(Collectors.toList()));
    }
  }
}
