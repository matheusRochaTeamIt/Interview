package com.interview.planningpoker.domain.repository;

import com.interview.planningpoker.domain.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
  void deleteAllByUserStoryId(Long userStoryId);
  
  Optional<Vote> findByUserIdAndUserStoryId(Long userId, Long userStoryId);
}
