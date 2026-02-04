package com.example.hellow_world.model;

import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")

public class UserSec {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
 @Column(unique = true)
private String username;
private String password;
private boolean enabled;
private boolean accountNotExpired;
private boolean accountNotLocked;
private boolean credentialNotExpired;

//usamos set porque no permite repetidos
// List permite repetidos
@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL) //el EAGER me va a cargar todos los roles
@JoinTable
        (name               = "user_roles",
         joinColumns        = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "role_id"))
private Set<Role> rolesList = new HashSet<>();

    public UserSec() {
    }

    public UserSec(Long id, String username, String password, boolean enabled, boolean accountNotExpired, boolean accountNotLocked, boolean credentialNotExpired, Set<Role> rolesList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNotExpired = accountNotExpired;
        this.accountNotLocked = accountNotLocked;
        this.credentialNotExpired = credentialNotExpired;
        this.rolesList = rolesList;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNotExpired() {
        return accountNotExpired;
    }

    public boolean isAccountNotLocked() {
        return accountNotLocked;
    }

    public boolean isCredentialNotExpired() {
        return credentialNotExpired;
    }

    public Set<Role> getRolesList() {
        return rolesList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public void setAccountNotLocked(boolean accountNotLocked) {
        this.accountNotLocked = accountNotLocked;
    }

    public void setCredentialNotExpired(boolean credentialNotExpired) {
        this.credentialNotExpired = credentialNotExpired;
    }

    public void setRolesList(Set<Role> rolesList) {
        this.rolesList = rolesList;
    }
}
