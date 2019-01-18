package com.epam.rdmanagement.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Aishwarya_Bommisetty
 *
 */

@Entity
@Table(name = "mentee")
public class MenteeEntity extends StudentEntity {

    @ManyToOne
    private MentorEntity mentor;

    public MentorEntity getMentor() {
        return mentor;
    }

    public void setMentor(MentorEntity mentor) {
        this.mentor = mentor;
    }

}