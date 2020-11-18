package com.example.getkracking.entities;

public class UserVO {
    private int id;
    private boolean deleted, verified;
    private String username, fullName, gender, email, avatarUrl, phone;
    private long dateCreated, dateLastActive, birthdate;

    public UserVO(int id, boolean deleted, boolean verified, String username, String fullName, String gender, String email, String avatarUrl, String phone, long dateCreated, long dateLastActive, long birthdate) {
        this.id = id;
        this.deleted = deleted;
        this.verified = verified;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.phone = phone;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(Long dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public Long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Long birthdate) {
        this.birthdate = birthdate;
    }
}
