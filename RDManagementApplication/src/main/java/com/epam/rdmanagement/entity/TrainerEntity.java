package com.epam.rdmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "trainer")
public class TrainerEntity extends UserEntity {

	@OneToMany(mappedBy = "trainer",fetch= FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<StudentEntity> students;

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	public void addStudent(StudentEntity student) {
		student.setTrainer(this);
		if (this.getStudents() == null) {

			students = new ArrayList<>();
		}
		this.getStudents().add(student);
	}

}
