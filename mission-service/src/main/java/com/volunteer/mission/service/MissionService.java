package com.volunteer.mission.service;

import com.volunteer.mission.dto.MissionDTO;
import com.volunteer.mission.model.Mission;
import com.volunteer.mission.model.MissionStatus;
import com.volunteer.mission.repository.MissionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MissionService {

    private final MissionRepository missionRepository;

    @Autowired
    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public Mission createMission(MissionDTO dto) {
        Mission mission = new Mission();
        mission.setRequesterId(dto.getRequesterId());
        mission.setTitle(dto.getTitle());
        mission.setDescription(dto.getDescription());
        mission.setLocation(dto.getLocation());
        mission.setUrgencyLevel(dto.getUrgencyLevel());
        return missionRepository.save(mission);
    }

    public List<Mission> getAvailableMissions() {
        return missionRepository.findByStatus(MissionStatus.PENDING);
    }

    public Mission getMission(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found"));
    }

    public List<Mission> getMissionsByRequester(Long requesterId) {
        return missionRepository.findByRequesterId(requesterId);
    }

    public List<Mission> getMissionsByVolunteer(Long volunteerId) {
        return missionRepository.findByVolunteerId(volunteerId);
    }

    public Mission assignVolunteer(Long missionId, Long volunteerId) {
        Mission mission = getMission(missionId);
        if (mission.getStatus() != MissionStatus.PENDING) {
            throw new IllegalStateException("Mission is not available");
        }
        mission.setVolunteerId(volunteerId);
        mission.setStatus(MissionStatus.ASSIGNED);
        return missionRepository.save(mission);
    }

    public Mission completeMission(Long missionId) {
        Mission mission = getMission(missionId);
        mission.setStatus(MissionStatus.COMPLETED);
        mission.setCompletedAt(LocalDateTime.now());
        return missionRepository.save(mission);
    }

    public Mission cancelMission(Long missionId) {
        Mission mission = getMission(missionId);
        if (mission.getStatus() == MissionStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed mission");
        }
        mission.setStatus(MissionStatus.CANCELLED);
        return missionRepository.save(mission);
    }
}