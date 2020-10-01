package com.example.meeting.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MatchedRepository extends JpaRepository<Matched, Long> {
}
