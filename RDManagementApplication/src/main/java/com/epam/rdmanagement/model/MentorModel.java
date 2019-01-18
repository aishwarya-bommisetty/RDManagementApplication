package com.epam.rdmanagement.model;

import java.util.List;

public class MentorModel extends UserProfileModel {

    private List<MenteeModel> mentees;

    public List<MenteeModel> getMentees() {
        return mentees;
    }

    public void setMentees(List<MenteeModel> mentees) {
        this.mentees = mentees;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((mentees == null) ? 0 : mentees.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MentorModel other = (MentorModel) obj;
        if (mentees == null) {
            if (other.mentees != null)
                return false;
        } else if (!mentees.equals(other.mentees))
            return false;
        return true;
    }

}
