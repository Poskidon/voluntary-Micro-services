package com.volunteer.volunteer.controller;

import com.volunteer.volunteer.dto.VolunteerDTO;
import com.volunteer.volunteer.model.Volunteer;
import com.volunteer.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@CrossOrigin(origins = "*")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody VolunteerDTO volunteerDTO) {
        volunteerDTO.setUserId(Long.parseLong(userId));
        return ResponseEntity.ok(volunteerService.createVolunteer(volunteerDTO));
    }

    @GetMapping("/profile")
    public ResponseEntity<Volunteer> getProfile(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(volunteerService.getVolunteer(Long.parseLong(userId)));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<Volunteer>> getVolunteersByArea(
            @PathVariable String area) {
        return ResponseEntity.ok(volunteerService.getVolunteersByArea(area));
    }

    @PutMapping("/profile")
    public ResponseEntity<Volunteer> updateProfile(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody VolunteerDTO volunteerDTO) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(Long.parseLong(userId), volunteerDTO));
    }

    @PutMapping("/deactivate")
    public ResponseEntity<Void> deactivateProfile(
            @RequestHeader("X-User-Id") String userId) {
        volunteerService.deactivateVolunteer(Long.parseLong(userId));
        return ResponseEntity.ok().build();
    }
}