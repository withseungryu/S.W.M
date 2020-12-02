package com.example.meeting.sms;


import com.example.meeting.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Phone findByToken(String token);
}
