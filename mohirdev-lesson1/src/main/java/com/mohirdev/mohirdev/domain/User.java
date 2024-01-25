package com.mohirdev.mohirdev.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name =" mohirdev_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    private String userName;
    private String password;
    private String surName;
    private String lastName;
    private String passpotSerie;
    private String number;
    private String passportJSHR;
    private String nation ;
    private String salary;
    private String addres;
    private boolean isActived = false;
    @Enumerated(EnumType.STRING)
    private User_Roles roles;

    public User() {
    }


    public User(String userName, String password, List<GrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.authorities = new HashSet<>(authorities.stream()
                .map(authority -> (Authority) authority)
                .collect(Collectors.toSet()));
    }

    @ManyToMany
    @JoinTable(
            name = "mohirdev_user_authority",

            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id" )
    )
    private Set<Authority> authorities = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User_Roles getRoles() {
        return roles;
    }

    public void setRoles(User_Roles roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPasspotSerie() {
        return passpotSerie;
    }

    public void setPasspotSerie(String passpotSerie) {
        this.passpotSerie = passpotSerie;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassportJSHR() {
        return passportJSHR;
    }

    public void setPassportJSHR(String passportJSHR) {
        this.passportJSHR = passportJSHR;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}