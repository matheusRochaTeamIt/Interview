package com.interview.planningpoker.v1.dto;

import com.interview.planningpoker.domain.model.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDTO {
  Long id;
  UserDTO creator;
  List<UserDTO> users;
  List<UserStoryDTO> stories = new ArrayList<>();
  
  public SessionDTO(Session ent) {
    id = ent.getId();
    creator = new UserDTO(ent.getCreator());
    users = ent.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    stories =
            ent.getStories().stream().map(UserStoryDTO::new)
                    .collect(Collectors.toList());
  }
}
