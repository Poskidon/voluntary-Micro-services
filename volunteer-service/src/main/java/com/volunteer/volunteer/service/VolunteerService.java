package com.volunteer.volunteer.service;

import com.volunteer.volunteer.dto.VolunteerDTO;
import com.volunteer.volunteer.model.Volunteer;
import com.volunteer.volunteer.model.VolunteerStatus;
import com.volunteer.volunteer.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer createVolunteer(VolunteerDTO volunteerDTO) {
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(volunteerDTO.getUserId());
        volunteer.setName(volunteerDTO.getName());
        volunteer.setAddress(volunteerDTO.getAddress());
        volunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
        volunteer.setAvailability(volunteerDTO.getAvailability());
        volunteer.setSkills(volunteerDTO.getSkills());
        volunteer.setPreferredAreas(volunteerDTO.getPreferredAreas());
        volunteer.setStatus(VolunteerStatus.ACTIVE);
        return volunteerRepository.save(volunteer);
    }

    public Volunteer getVolunteer(Long userId) {
        return volunteerRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Volunteer not found"));
    }

    public List<Volunteer> getVolunteersByArea(String area) {
        return volunteerRepository.findByPreferredAreasContainingAndStatus(
                area, VolunteerStatus.ACTIVE
        );
    }

    public Volunteer updateVolunteer(Long userId, VolunteerDTO volunteerDTO) {
        Volunteer volunteer = getVolunteer(userId);
        volunteer.setName(volunteerDTO.getName());
        volunteer.setAddress(volunteerDTO.getAddress());
        volunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
        volunteer.setAvailability(volunteerDTO.getAvailability());
        volunteer.setSkills(volunteerDTO.getSkills());
        volunteer.setPreferredAreas(volunteerDTO.getPreferredAreas());
        return volunteerRepository.save(volunteer);
    }

    public void deactivateVolunteer(Long userId) {
        Volunteer volunteer = getVolunteer(userId);
        volunteer.setStatus(VolunteerStatus.INACTIVE);
        volunteer.setActive(false);
        volunteerRepository.save(volunteer);
    }
}