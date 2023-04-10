package com.interview.planningpoker.domain.repository;

import com.interview.planningpoker.domain.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
