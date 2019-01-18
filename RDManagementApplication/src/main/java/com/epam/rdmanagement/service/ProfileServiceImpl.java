package com.epam.rdmanagement.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.rdmanagement.entity.FeedbackEntity;
import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.FeedbackAddModel;
import com.epam.rdmanagement.model.RoleModel;
import com.epam.rdmanagement.repository.FeedbackRepository;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * The Class ProfileServiceImpl.
 *
 * @author Prabhudeep_Banga
 */

@Service
public class ProfileServiceImpl implements ProfileService {

    /** The user repository. */
    @Autowired
    UserRepository userRepository;

    /** The feedback repository. */
    @Autowired
    FeedbackRepository feedbackRepository;

    /** The could not add admin exception message. */
    @Value("${could-not-add-admin.message}")
    private String couldNotAddAdminExceptionMessage;

    /*
     * (non-Javadoc)
     * 
     * @see com.epam.app.service.ProfileService#getRole(java.lang.String)
     */
    @Override
    public RoleModel getRole(String userEmail) {

        RoleEntity roleEntity = userRepository.findByEmail(userEmail).getRole();
        return new DozerBeanMapper().map(roleEntity, RoleModel.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.epam.rdmanagement.service.ProfileService#giveFeedback(com.epam.rdmanagement.model.FeedbackAddModel)
     */
    @Override
    public boolean giveFeedback(FeedbackAddModel feedbackAddModel) throws OperationCouldNotBeProcessed {

        FeedbackEntity feedbackEntity = new FeedbackEntity();

        UserEntity fromUser = userRepository.findByEmail(feedbackAddModel.getFromUserEmail());
        UserEntity toUser = userRepository.findByEmail(feedbackAddModel.getToUserEmail());

        feedbackEntity.setFromUser(fromUser);
        feedbackEntity.setToUser(toUser);
        feedbackEntity.setFeedback(feedbackAddModel.getFeedback());

        feedbackRepository.save(feedbackEntity);

        if (feedbackRepository.findByFromUserAndToUser(fromUser, toUser) == null) {
            throw new OperationCouldNotBeProcessed(couldNotAddAdminExceptionMessage);
        }

        fromUser.giveFeedback(feedbackEntity);
        return true;

    }

}
