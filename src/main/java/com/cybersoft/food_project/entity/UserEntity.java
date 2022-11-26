package com.cybersoft.food_project.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "token")
    private String token;

    @Column(name = "type_token")
    private String typeToken;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "verify_code")
    private String verifyCode;

    @Column(name = "verify_code_expired")
    private String verifyCodeExpired;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToOne(mappedBy = "user")
    private UserDetailEntity userDetail;

    @OneToMany(mappedBy = "user")
    private Set<FoodReviewEntity> foodReviews;

    @OneToMany(mappedBy = "user")
    private Set<TOtherEntity> tOthers;

    public Set<TOtherEntity> gettOthers() {
        return tOthers;
    }

    public void settOthers(Set<TOtherEntity> tOthers) {
        this.tOthers = tOthers;
    }

    public Set<FoodReviewEntity> getFoodReviews() {
        return foodReviews;
    }

    public void setFoodReviews(Set<FoodReviewEntity> foodReviews) {
        this.foodReviews = foodReviews;
    }

    public UserDetailEntity getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailEntity userDetail) {
        this.userDetail = userDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTypeToken() {
        return typeToken;
    }

    public void setTypeToken(String typeToken) {
        this.typeToken = typeToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getVerifyCodeExpired() {
        return verifyCodeExpired;
    }

    public void setVerifyCodeExpired(String verifyCodeExpired) {
        this.verifyCodeExpired = verifyCodeExpired;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}