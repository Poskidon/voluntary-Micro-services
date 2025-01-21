// src/main/java/com/volunteer/requester/service/RequesterService.java

package com.volunteer.requester.service;

import com.volunteer.requester.dto.RequesterDTO;
import com.volunteer.requester.model.Requester;
import com.volunteer.requester.repository.RequesterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RequesterService {
    private static final Logger logger = LoggerFactory.getLogger(RequesterService.class);
    private final RequesterRepository requesterRepository;

    @Autowired
    public RequesterService(RequesterRepository requesterRepository) {
        this.requesterRepository = requesterRepository;
    }

    public Requester createRequester(RequesterDTO requesterDTO) {
        logger.debug("Creating requester for userId: {}", requesterDTO.getUserId());
        try {
            // Check if requester already exists
            if (requesterRepository.findByUserId(requesterDTO.getUserId()).isPresent()) {
                logger.error("Requester already exists for userId: {}", requesterDTO.getUserId());
                throw new RuntimeException("Un profil existe déjà pour cet utilisateur");
            }

            Requester requester = new Requester();
            requester.setUserId(requesterDTO.getUserId());
            requester.setName(requesterDTO.getName());
            requester.setAddress(requesterDTO.getAddress());
            requester.setPhoneNumber(requesterDTO.getPhoneNumber());
            requester.setEmergencyContact(requesterDTO.getEmergencyContact());
            requester.setNotes(requesterDTO.getNotes());
            requester.setActive(true);

            Requester savedRequester = requesterRepository.save(requester);
            logger.debug("Requester created successfully with ID: {}", savedRequester.getId());
            return savedRequester;
        } catch (Exception e) {
            logger.error("Error creating requester: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Requester getRequester(Long userId) {
        logger.debug("Getting requester for userId: {}", userId);
        try {
            Requester requester = requesterRepository.findByUserId(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Requester not found"));
            logger.debug("Found requester: {}", requester.getId());
            return requester;
        } catch (Exception e) {
            logger.error("Error getting requester: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Requester> getRequestHistory(Long userId) {
        logger.debug("Getting request history for userId: {}", userId);
        try {
            List<Requester> history = requesterRepository.findByUserId(userId)
                    .map(List::of)
                    .orElseThrow(() -> new EntityNotFoundException("No history found"));
            logger.debug("Found {} history entries", history.size());
            return history;
        } catch (Exception e) {
            logger.error("Error getting request history: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Requester updateRequester(Long userId, RequesterDTO requesterDTO) {
        logger.debug("Updating requester for userId: {}", userId);
        try {
            Requester requester = getRequester(userId);

            requester.setName(requesterDTO.getName());
            requester.setAddress(requesterDTO.getAddress());
            requester.setPhoneNumber(requesterDTO.getPhoneNumber());
            requester.setEmergencyContact(requesterDTO.getEmergencyContact());
            requester.setNotes(requesterDTO.getNotes());

            Requester updatedRequester = requesterRepository.save(requester);
            logger.debug("Requester updated successfully");
            return updatedRequester;
        } catch (Exception e) {
            logger.error("Error updating requester: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deactivateRequester(Long userId) {
        logger.debug("Deactivating requester for userId: {}", userId);
        try {
            Requester requester = getRequester(userId);
            requester.setActive(false);
            requesterRepository.save(requester);
            logger.debug("Requester deactivated successfully");
        } catch (Exception e) {
            logger.error("Error deactivating requester: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Requester> getAllActiveRequesters() {
        logger.debug("Getting all active requesters");
        try {
            List<Requester> activeRequesters = requesterRepository.findByActiveTrue();
            logger.debug("Found {} active requesters", activeRequesters.size());
            return activeRequesters;
        } catch (Exception e) {
            logger.error("Error getting active requesters: {}", e.getMessage(), e);
            throw e;
        }
    }
}