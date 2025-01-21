package com.volunteer.requester.repository;

import com.volunteer.requester.model.Requester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequesterRepository extends JpaRepository<Requester, Long> {
    Optional<Requester> findByUserId(Long userId);
    List<Requester> findByActiveTrue();
}