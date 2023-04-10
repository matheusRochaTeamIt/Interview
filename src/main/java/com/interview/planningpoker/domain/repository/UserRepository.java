package com.interview.planningpoker.domain.repository;

import com.interview.planningpoker.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByNickname(String nickName);
  
  List<User> findAllBySession_Id(Long sessionId);
}
