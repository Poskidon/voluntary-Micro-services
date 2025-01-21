package com.volunteer.requester.controller;

import com.volunteer.requester.dto.RequesterDTO;
import com.volunteer.requester.model.Requester;
import com.volunteer.requester.service.RequesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requesters")
@CrossOrigin(origins = "*")
public class RequesterController {

    private static final Logger logger = LoggerFactory.getLogger(RequesterController.class);
    private final RequesterService requesterService;

    @Autowired
    public RequesterController(RequesterService requesterService) {
        this.requesterService = requesterService;
    }

    @PostMapping
    public ResponseEntity<?> createRequester(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody RequesterDTO requesterDTO) {
        try {
            logger.debug("Creating requester for userId: {}", userId);
            requesterDTO.setUserId(Long.parseLong(userId));
            Requester requester = requesterService.createRequester(requesterDTO);
            logger.debug("Requester created successfully with ID: {}", requester.getId());
            return ResponseEntity.ok(requester);
        } catch (Exception e) {
            logger.error("Error creating requester: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la création du profil: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("X-User-Id") String userId) {
        try {
            logger.debug("Getting profile for userId: {}", userId);
            Requester requester = requesterService.getRequester(Long.parseLong(userId));
            logger.debug("Profile found for userId: {}", userId);
            return ResponseEntity.ok(requester);
        } catch (Exception e) {
            logger.error("Error retrieving profile for userId {}: {}", userId, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la récupération du profil: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody RequesterDTO requesterDTO) {
        try {
            logger.debug("Updating profile for userId: {}", userId);
            requesterDTO.setUserId(Long.parseLong(userId));
            Requester updatedRequester = requesterService.updateRequester(Long.parseLong(userId), requesterDTO);
            logger.debug("Profile updated successfully for userId: {}", userId);
            return ResponseEntity.ok(updatedRequester);
        } catch (Exception e) {
            logger.error("Error updating profile for userId {}: {}", userId, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la mise à jour du profil: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getRequestHistory(@RequestHeader("X-User-Id") String userId) {
        try {
            logger.debug("Getting request history for userId: {}", userId);
            List<Requester> history = requesterService.getRequestHistory(Long.parseLong(userId));
            logger.debug("Found {} history entries for userId: {}", history.size(), userId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            logger.error("Error retrieving history for userId {}: {}", userId, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la récupération de l'historique: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<?> deactivateProfile(@RequestHeader("X-User-Id") String userId) {
        try {
            logger.debug("Deactivating profile for userId: {}", userId);
            requesterService.deactivateRequester(Long.parseLong(userId));
            logger.debug("Profile deactivated successfully for userId: {}", userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Profil désactivé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deactivating profile for userId {}: {}", userId, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la désactivation du profil: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveRequesters() {
        try {
            logger.debug("Getting all active requesters");
            List<Requester> activeRequesters = requesterService.getAllActiveRequesters();
            logger.debug("Found {} active requesters", activeRequesters.size());
            return ResponseEntity.ok(activeRequesters);
        } catch (Exception e) {
            logger.error("Error retrieving active requesters: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la récupération des demandeurs actifs: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}