package com.interview.planningpoker.v1.service;

import com.interview.planningpoker.domain.model.Session;
import com.interview.planningpoker.domain.model.User;
import com.interview.planningpoker.domain.model.UserStory;
import com.interview.planningpoker.domain.repository.SessionRepository;
import com.interview.planningpoker.domain.repository.UserRepository;
import com.interview.planningpoker.domain.repository.UserStoryRepository;
import com.interview.planningpoker.exception.ApiException;
import com.interview.planningpoker.v1.dto.SessionDTO;
import com.interview.planningpoker.v1.dto.UserDTO;
import com.interview.planningpoker.v1.dto.UserStoryCreateDTO;
import com.interview.planningpoker.v1.dto.UserStoryDTO;
import com.interview.planningpoker.v1.dto.VoteDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.interview.planningpoker.domain.enumeration.UserStoriesStatusEnum.VOTED;
import static com.interview.planningpoker.exception.messages.Messages.ERROR_USER_TRYING_TO_UPDATE_OUT_OF_BOUNDS_USER_STORY;
import static com.interview.planningpoker.exception.messages.Messages.SESSION_NOT_FOUND;
import static com.interview.planningpoker.exception.messages.Messages.USER_NOT_FOUND;
import static com.interview.planningpoker.exception.messages.Messages.USER_STORY_NOT_FOUND;
import static com.interview.planningpoker.exception.messages.Messages.USER_STORY_NOT_FOUND_OR_ALREADY_VOTED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class SessionService {
  
  SessionRepository repository;
  UserRepository userRepository;
  UserStoryRepository storyRepository;
  
  public List<SessionDTO> findAll() {
    return repository.findAll().stream().map(SessionDTO::new).collect(Collectors.toList());
  }
  
  public SessionDTO createSession(SessionDTO dto) {
    Session session = new Session();
    session.setCreator(new User(dto.getCreator(), session));
    return new SessionDTO(this.repository.save(session));
  }
  
  public SessionDTO findById(Long id) {
    return new SessionDTO(repository.findById(id).orElseThrow(() -> new ApiException(NOT_FOUND,
            SESSION_NOT_FOUND)));
  }
  
  public List<UserDTO> findSessionMembers(Long idSession) {
    return userRepository.findAllBySession_Id(idSession).stream().map(UserDTO::new)
            .collect(Collectors.toList());
  }
  
  
  public SessionDTO joinSession(Long idSession, UserDTO dto) {
    Session s = repository.findById(idSession).orElseThrow(() ->
            new ApiException(NOT_FOUND, SESSION_NOT_FOUND));
    s.getUsers().add(new User(dto, s));
    return new SessionDTO(repository.save(s));
  }
  
  public void logoutMember(Long idSession, Long memberId) {
    Session s = repository.findById(idSession).orElseThrow(() ->
            new ApiException(NOT_FOUND, SESSION_NOT_FOUND));
    s.getUsers().remove(s.getUsers().stream().filter(u -> u.getId().equals(memberId)).findFirst()
            .orElseThrow(() -> new ApiException(NOT_FOUND, USER_NOT_FOUND)));
    repository.save(s);
  }
  
  public List<UserStoryDTO> findAllUserStories(Long idSession) {
    return storyRepository.findAllBySession_Id(idSession).stream().map(UserStoryDTO::new)
            .collect(Collectors.toList());
  }
  
  public UserStoryDTO createUserStory(Long idSession, UserStoryCreateDTO dto) {
    Session session = repository.findById(idSession).orElseThrow(() -> new ApiException(NOT_FOUND,
            SESSION_NOT_FOUND));
    UserStory userStory = dto.toEntity(session);
    return new UserStoryDTO(storyRepository.save(userStory));
  }
  
  public UserStoryDTO updateUserStory(Long idSession, Long idUserStory, UserStoryDTO dto) {
    UserStory userStory = storyRepository.findById(idUserStory).orElseThrow(() -> new ApiException(NOT_FOUND,
            USER_STORY_NOT_FOUND));
    if (!userStory.getSession().getId().equals(idSession)) {
      throw new ApiException(FORBIDDEN, ERROR_USER_TRYING_TO_UPDATE_OUT_OF_BOUNDS_USER_STORY);
    }
    userStory.updateData(dto);
    return new UserStoryDTO(storyRepository.save(userStory));
  }
  
  @Transactional
  public void deleteUserStory(Long idSession, Long idUserStory) {
    storyRepository.deleteUserStoryNative(idSession, idUserStory);
  }
  
  public List<VoteDTO> findAllEmittedVotes(Long idSession, Long idUserStory) {
    return this.findById(idSession)
            .getStories().stream().filter(us -> us.getId().equals(idUserStory))
            .findFirst().orElseThrow(() -> new ApiException(NOT_FOUND, USER_STORY_NOT_FOUND)).getVotes();
  }
  
  public void doVote(@PathVariable Long idSession,
                     @PathVariable Long idUserStory,
                     VoteDTO dto) {
    Session s = repository.findById(idSession).orElseThrow(() -> new ApiException(NOT_FOUND, SESSION_NOT_FOUND));
    UserStory userStory = findAvailableUserStory(idUserStory, s);
    s.getStories().remove(userStory);
    addVoteToStory(dto, s, userStory);
    s.getStories().add(userStory);
    repository.save(s);
  }
  
  private static void addVoteToStory(VoteDTO dto, Session s, UserStory userStory) {
    userStory.getVotes().stream()
            .filter(v -> v.getUser().getId().equals(dto.getUser().getId()))
            .findFirst().ifPresent(vote -> userStory.getVotes().remove(vote));
    userStory.getVotes()
            .add(dto.toEnt(s.getUsers()
                    .stream().filter(user -> user.getId().equals(dto.getUser().getId()))
                    .findFirst().orElseThrow(() -> new ApiException(NOT_FOUND, USER_NOT_FOUND)), s, userStory));
  }
  
  private static UserStory findAvailableUserStory(Long idUserStory, Session s) {
    return s.getStories().stream().filter(us -> us.getId().equals(idUserStory)
                    && !us.getStatus().equals(VOTED))
            .findFirst().orElseThrow(() -> new ApiException(NOT_FOUND, USER_STORY_NOT_FOUND_OR_ALREADY_VOTED));
  }
  
  public void destroySession(Long idSession) {
    repository.deleteById(idSession);
  }
}
