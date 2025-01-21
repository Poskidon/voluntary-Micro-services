package com.volunteer.mission.repository;

import com.volunteer.mission.model.Mission;
import com.volunteer.mission.model.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByRequesterId(Long requesterId);
    List<Mission> findByVolunteerId(Long volunteerId);
    List<Mission> findByStatus(MissionStatus status);
    List<Mission> findByLocationAndStatus(String location, MissionStatus status);
}