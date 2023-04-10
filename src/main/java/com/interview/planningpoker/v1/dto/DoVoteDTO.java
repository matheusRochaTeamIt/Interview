package com.interview.planningpoker.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoVoteDTO {
  private Long id;
  private UserDTO user;
  private String value;
  private Long userStoryId;
  
}
