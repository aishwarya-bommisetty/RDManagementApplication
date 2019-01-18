package com.epam.rdmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<FeedbackEntity> givenFeedbacks;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<FeedbackEntity> recievedFeedbacks;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleEntity role;

    @Column(name = "password")
    private String password;

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FeedbackEntity> getGivenFeedbacks() {
        return givenFeedbacks;
    }

    public void setGivenFeedbacks(List<FeedbackEntity> givenFeedbacks) {
        this.givenFeedbacks = givenFeedbacks;
    }

    public List<FeedbackEntity> getRecievedFeedbacks() {
        return recievedFeedbacks;
    }

    public void setRecievedFeedbacks(List<FeedbackEntity> recievedFeedbacks) {
        this.recievedFeedbacks = recievedFeedbacks;
    }

    public void giveFeedback(FeedbackEntity feedbackEntity) {
        
        if(this.givenFeedbacks == null) {
            this.givenFeedbacks = new ArrayList<>();
        }
        this.givenFeedbacks.add(feedbackEntity);
        
        if(feedbackEntity.getToUser().getRecievedFeedbacks() == null) {
            feedbackEntity.getToUser().setRecievedFeedbacks(new ArrayList<>());
        }
        feedbackEntity.getToUser().getRecievedFeedbacks().add(feedbackEntity);
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", lastName=" + lastName + ", role=" + role.getRoleName()
                + ", password=" + password + "]";
    }

}
