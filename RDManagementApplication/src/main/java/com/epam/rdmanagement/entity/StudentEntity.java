package com.epam.rdmanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class StudentEntity extends UserEntity {



	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name = "trainer_id")
	private TrainerEntity trainer;

	@Override
	public String toString() {
		return "Student [trainer=" + trainer + "]";
	}

	public TrainerEntity getTrainer() {
		return trainer;
	}

	public void setTrainer(TrainerEntity trainer) {
		this.trainer = trainer;
	}

	

}
