package com.interview.planningpoker.v1.dto;

import com.interview.planningpoker.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
  Long id;
  String nickName;
  
  public UserDTO(User ent) {
    id = ent.getId();
    nickName = ent.getNickname();
  }
}
