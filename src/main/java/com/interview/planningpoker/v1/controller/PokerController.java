package com.interview.planningpoker.v1.controller;

import com.interview.planningpoker.v1.dto.SessionDTO;
import com.interview.planningpoker.v1.dto.UserDTO;
import com.interview.planningpoker.v1.dto.UserStoryCreateDTO;
import com.interview.planningpoker.v1.dto.UserStoryDTO;
import com.interview.planningpoker.v1.dto.VoteDTO;
import com.interview.planningpoker.v1.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@Api(value = "Address", tags = "Address Controller")
@RequestMapping(value = "/sessions")
public class PokerController {
  SessionService sessionService;
  
  @ApiOperation(value = "Find All Sessions.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @GetMapping
  @ResponseBody
  public List<SessionDTO> findAllSessions() {
    return sessionService.findAll();
  }
  
  @ApiOperation(value = "Create a new Session.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @PostMapping
  @ResponseBody
  public SessionDTO createSession(@Valid @RequestBody SessionDTO sessionDTO) {
    return sessionService.createSession(sessionDTO);
  }
  
  @ApiOperation(value = "Find Session Information.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @GetMapping(value = "/{idSession}")
  @ResponseBody
  public SessionDTO findSession(@PathVariable Long idSession) {
    return sessionService.findById(idSession);
  }
  
  @ApiOperation(value = "Destroy a selected Session.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @DeleteMapping(value = "/{idSession}")
  @ResponseBody
  public void destroySession(@PathVariable Long idSession) {
    sessionService.destroySession(idSession);
  }
  
  @ApiOperation(value = "Find all MEMBERS that are logged in a session.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @GetMapping(value = "/{idSession}/members")
  @ResponseBody
  public List<UserDTO> findSessionMembers(@PathVariable Long idSession) {
    return sessionService.findSessionMembers(idSession);
  }
  
  @ApiOperation(value = "Join a SELECTED Session.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @PostMapping(value = "/{idSession}/members")
  @ResponseBody
  public SessionDTO joinSession(@PathVariable Long idSession,
                                @RequestBody UserDTO user) {
    return sessionService.joinSession(idSession, user);
  }
  
  @ApiOperation(value = "Logout a SELECTED Member.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @GetMapping(value = "/{idSession}/members/{idMember}")
  @ResponseBody
  public void logoutMember(@PathVariable Long idSession,
                           @PathVariable Long idMember) {
    sessionService.logoutMember(idSession, idMember);
  }
  
  @ApiOperation(value = "Find ALL User Stories Still Available to be voted.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @GetMapping(value = "/{idSession}/stories")
  @ResponseBody
  public List<UserStoryDTO> findAllUserStories(@PathVariable Long idSession) {
    return sessionService.findAllUserStories(idSession);
  }
  
  @ApiOperation(value = "Create a new User Story.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @PostMapping(value = "/{idSession}/stories")
  @ResponseBody
  public UserStoryDTO createUserStory(@PathVariable Long idSession,
                                      @RequestBody UserStoryCreateDTO dto) {
    return sessionService.createUserStory(idSession, dto);
  }
  
  @ApiOperation(value = "Update a SELECTED User Story Information.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @PutMapping(value = "/{idSession}/stories/{idUserStory}")
  @ResponseBody
  public UserStoryDTO updateStoryInformation(@PathVariable Long idSession,
                                             @PathVariable Long idUserStory,
                                             @RequestBody UserStoryDTO dto) {
    return sessionService.updateUserStory(idSession, idUserStory, dto);
  }
  
  @ApiOperation(value = "Delete a SELECTED User Story.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @DeleteMapping(value = "/{idSession}/stories/{idUserStory}")
  @ResponseBody
  public void deleteStory(@PathVariable Long idSession,
                          @PathVariable Long idUserStory) {
    sessionService.deleteUserStory(idSession, idUserStory);
  }
  
  @ApiOperation(value = "Find All EMITTED Votes.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @GetMapping(value = "/{idSession}/votes/{idUserStory}")
  @ResponseBody
  public List<VoteDTO> findAllEmittedVotes(@PathVariable Long idSession,
                                           @PathVariable Long idUserStory) {
    return sessionService.findAllEmittedVotes(idSession, idUserStory);
  }
  
  @ApiOperation(value = "DoVote.")
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Ok"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Internal server error")
  })
  @PostMapping(value = "/{idSession}/votes/{idUserStory}")
  @ResponseBody
  public void doVote(@PathVariable Long idSession,
                     @PathVariable Long idUserStory, @RequestBody VoteDTO dto) {
    sessionService.doVote(idSession, idUserStory, dto);
  }
}
