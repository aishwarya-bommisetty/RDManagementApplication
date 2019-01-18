package com.epam.rdmanagement.model;

import java.util.List;

public class TrainerModel extends UserProfileModel {

    private List<StudentModel> students;

    public List<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(List<StudentModel> students) {
        this.students = students;
    }

}
