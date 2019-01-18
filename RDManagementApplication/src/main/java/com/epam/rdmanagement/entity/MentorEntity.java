package com.epam.rdmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mentor")
public class MentorEntity extends UserEntity {

	@OneToMany(mappedBy = "mentor", fetch = FetchType.EAGER)
	private List<MenteeEntity> mentees;

	public List<MenteeEntity> getMentees() {
		return mentees;
	}

	public void setMentees(List<MenteeEntity> mentees) {
		this.mentees = mentees;
	}

	public void addMentee(MenteeEntity mentee) {
		mentee.setMentor(this);
		if (this.getMentees() == null) {
			mentees = new ArrayList<>();
		}
		this.getMentees().add(mentee);
	}

	public void removeMentee(MenteeEntity mentee) {
		mentee.setMentor(null);
		this.getMentees().remove(mentee);
	}
}
