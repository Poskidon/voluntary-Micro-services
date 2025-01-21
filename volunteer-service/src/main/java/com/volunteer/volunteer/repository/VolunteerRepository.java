package com.volunteer.volunteer.repository;

import com.volunteer.volunteer.model.Volunteer;
import com.volunteer.volunteer.model.VolunteerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findByUserId(Long userId);
    List<Volunteer> findByStatus(VolunteerStatus status);
    List<Volunteer> findByPreferredAreasContainingAndStatus(String area, VolunteerStatus status);
    List<Volunteer> findByStatusAndIsActive(VolunteerStatus status, boolean isActive);
}