package com.epam.rdmanagement.model;

public class FeedbackModel {

    private int id;

    private String feedbacks;

    public int getId() {
        return id;
    }

    public void setId(int feedbackId) {
        this.id = feedbackId;
    }

    public String getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String feedback) {
        this.feedbacks = feedback;
    }
}
