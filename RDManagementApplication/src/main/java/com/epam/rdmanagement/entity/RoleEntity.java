package com.epam.rdmanagement.entity;

import com.epam.rdmanagement.util.ConstantsUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the role database table.
 *
 */
@Entity
@Table(name = ConstantsUtil.ROLE_TABLE_NAME)
public class RoleEntity {
  @Id
  @Column(name = ConstantsUtil.ROLE_ID_COLUMN_NAME)
  private int roleId;
  @Column(name = ConstantsUtil.ROLE_NAME_COLUMN_NAME)
  private String roleName;

  public RoleEntity() {
  }

  public RoleEntity(int roleId) {
    super();
    this.roleId = roleId;
  }

  public RoleEntity(int roleId, String roleName) {
    this.roleId = roleId;
    this.roleName = roleName;
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

    RoleEntity other = (RoleEntity) obj;

    if (roleId != other.roleId) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + roleId;

    return result;
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
}
