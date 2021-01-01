package com.example.meeting.dao;


import com.example.meeting.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Phone findByToken(String token);
}
