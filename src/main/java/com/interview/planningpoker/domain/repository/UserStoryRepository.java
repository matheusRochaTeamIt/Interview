package com.interview.planningpoker.domain.repository;

import com.interview.planningpoker.domain.model.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
  List<UserStory> findAllBySession_Id(Long sessionId);
  
  @Modifying
  @Query(value =
          "delete from vote where session_id = :sessionId ;"
                  + "DELETE FROM user_story WHERE session_id = :sessionId AND id = :id ;"
          
          , nativeQuery = true)
  void deleteUserStoryNative(@Param("sessionId") Long sessionId, @Param("id") Long id);
}
