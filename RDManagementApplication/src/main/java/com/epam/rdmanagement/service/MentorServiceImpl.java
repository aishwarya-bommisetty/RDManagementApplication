package com.epam.rdmanagement.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.rdmanagement.entity.MenteeEntity;
import com.epam.rdmanagement.entity.MentorEntity;
import com.epam.rdmanagement.exception.MenteesNotFoundException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.MentorModel;
import com.epam.rdmanagement.repository.MentorRepository;
import com.epam.rdmanagement.repository.UserRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class MentorServiceImpl.
 *
 * @author Prabhudeep_Banga
 */
@Service
public class MentorServiceImpl implements MentorService {

    /** The user repository. */
    @Autowired
    UserRepository userRepository;

    /** The mentor repository. */
    @Autowired
    MentorRepository mentorRepository;

    /** The mentess not found exception message. */
    @Value("${mentees-not-found.message}")
    private String mentessNotFoundExceptionMessage;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.epam.app.service.MentorService#getAllMenteesUnderMentor(java.lang.String)
     */
    @Override
    public List<MenteeModel> getAllMenteesUnderMentor(String mentorEmail) throws MenteesNotFoundException {

        MentorEntity mentor = mentorRepository.findByEmail(mentorEmail);

        if (isMenteeListEmpty(mentor)) {
            throw new MenteesNotFoundException("mentee not found");
        } else {
            List<MenteeEntity> mentees = mentor.getMentees();
            List<MenteeModel> models = new ArrayList<>();
            Iterator<MenteeEntity> iterator = mentees.iterator();

            MenteeEntity menteeEntity;
            MenteeModel menteeModel = new MenteeModel();

            while (iterator.hasNext()) {
                menteeEntity = iterator.next();
                menteeModel.setFirstName(menteeEntity.getFirstName());
                menteeModel.setLastName(menteeEntity.getLastName());
                menteeModel.setEmail(menteeEntity.getEmail());
                models.add(menteeModel);
            }

            return models;
        }
    }

    /**
     * Checks if is mentee list empty.
     *
     * @param mentor the mentor
     * @return true, if mentee list is empty. false, if mentee list is not empty.
     */
    private boolean isMenteeListEmpty(MentorEntity mentor) {

        return mentor.getMentees().isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.epam.rdmanagement.service.MentorService#getProfile(java.lang.String)
     */
    @Override
    public MentorModel getProfile(String mentorEmail) {

        MentorEntity mentorEntity = mentorRepository.findByEmail(mentorEmail);
        return new DozerBeanMapper().map(mentorEntity, MentorModel.class);
    }

}
