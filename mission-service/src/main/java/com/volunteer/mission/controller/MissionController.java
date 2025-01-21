package com.volunteer.mission.controller;

import com.volunteer.mission.dto.MissionDTO;
import com.volunteer.mission.model.Mission;
import com.volunteer.mission.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/missions")
@CrossOrigin(origins = "*")
public class MissionController {

    private final MissionService missionService;

    @Autowired
    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @PostMapping
    public ResponseEntity<Mission> createMission(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody MissionDTO missionDTO) {
        missionDTO.setRequesterId(Long.parseLong(userId));
        return ResponseEntity.ok(missionService.createMission(missionDTO));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Mission>> getAvailableMissions() {
        return ResponseEntity.ok(missionService.getAvailableMissions());
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<Mission>> getMyMissions(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(missionService.getMissionsByRequester(Long.parseLong(userId)));
    }

    @GetMapping("/my-missions")
    public ResponseEntity<List<Mission>> getMyVolunteerMissions(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(missionService.getMissionsByVolunteer(Long.parseLong(userId)));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Mission> acceptMission(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") String volunteerId) {
        return ResponseEntity.ok(missionService.assignVolunteer(id, Long.parseLong(volunteerId)));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Mission> completeMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.completeMission(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Mission> cancelMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.cancelMission(id));
    }
}