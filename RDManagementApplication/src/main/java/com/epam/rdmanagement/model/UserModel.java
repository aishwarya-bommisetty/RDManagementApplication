package com.epam.rdmanagement.model;

import com.epam.rdmanagement.util.ConstantsUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserModel {
  @NotNull(message = "{invalid-email.message}")
  @Pattern(regexp = ConstantsUtil.EMAIL_PATTERN, message = "{invalid-email.message}")
  private String email;
  @NotNull(message = "{invalid-name.message}")
  @Pattern(regexp = ConstantsUtil.NAME_PATTERN, message = "{invalid-name.message}")
  private String firstName;
  @NotNull(message = "{invalid-name.message}")
  @Pattern(regexp = ConstantsUtil.NAME_PATTERN, message = "{invalid-name.message}")
  private String lastName;
  @NotNull(message = "{invalid-password.message}")
  @Pattern(regexp = ConstantsUtil.PASSWORD_PATTERN, message = "{invalid-password.message}")
  private String password;

  public UserModel() {
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    UserModel other = (UserModel) obj;

    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }

    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }

    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }

    if (password == null) {
      if (other.password != null) {
        return false;
      }
    } else if (!password.equals(other.password)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());

    return result;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
