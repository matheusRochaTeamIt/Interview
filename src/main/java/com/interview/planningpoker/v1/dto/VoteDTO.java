package com.interview.planningpoker.v1.dto;

import com.interview.planningpoker.domain.model.Session;
import com.interview.planningpoker.domain.model.User;
import com.interview.planningpoker.domain.model.UserStory;
import com.interview.planningpoker.domain.model.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteDTO {
  private Long id;
  private UserDTO user;
  private String value;
  
  VoteDTO(Vote ent) {
    id = ent.getId();
    user = new UserDTO(ent.getUser());
    value = ent.getValue();
  }
  
  public Vote toEnt(User u, Session s, UserStory us) {
    Vote v = new Vote();
    v.setValue(value);
    v.setUser(u);
    v.setSession(s);
    v.setUserStory(us);
    return v;
  }
}
