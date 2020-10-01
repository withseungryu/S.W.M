package com.example.meeting.bookmark;

import com.example.meeting.match.Matched;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
