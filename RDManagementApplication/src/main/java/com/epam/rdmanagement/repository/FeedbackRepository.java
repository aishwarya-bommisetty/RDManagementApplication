package com.epam.rdmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.rdmanagement.entity.FeedbackEntity;
import com.epam.rdmanagement.entity.UserEntity;

/**
 * The Interface FeedbackRepository.
 * 
 * @author Aishwarya_Bommisetty
 */

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {

    public FeedbackEntity findByFromUserAndToUser(UserEntity fromUser, UserEntity toUser);
}
