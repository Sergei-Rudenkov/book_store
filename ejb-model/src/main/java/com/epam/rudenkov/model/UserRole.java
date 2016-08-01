package com.epam.rudenkov.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sergei-rudenkov on 12.7.16.
 */
@Entity
@XmlRootElement
@Table(name = "UserRoleHibernate", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class UserRole {

    @Id
    private Long user_id;

    @NotNull
    @Column(name = "user_role")
    private String role;

    @Column(name = "role_group")
    private String groupName;

    public enum Role {
        SELLER, BUYER
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user_id=" + user_id +
                ", role='" + role + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
