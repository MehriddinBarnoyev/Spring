package com.mohirdev.mohirdev.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "mohirdev_authority")
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Use a proper identifier

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<User> users;

    // Constructors

    public Authority() {
        // Default constructor
    }

    public Authority(String name) {
        this.name = name;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

}
