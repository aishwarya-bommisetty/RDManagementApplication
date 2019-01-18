package com.epam.rdmanagement.model;

public class StudentModel extends UserProfileModel{

	
	private String trainer;


	public String getTrainer() {
		return trainer;
	}


	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}


	@Override
	public String toString() {
		return "StudentModel [trainer=" + trainer + ", getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + ", getEmail()=" + getEmail() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentModel other = (StudentModel) obj;
        if (trainer == null) {
            if (other.trainer != null)
                return false;
        } else if (!trainer.equals(other.trainer))
            return false;
        return true;
    }
	
}
