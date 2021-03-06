package com.rideread.rideread.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class UserData implements Serializable{

    private int uid;
    private int sex;
    private String school;
    private String phonenumber;
    private String updated_at;
    private int follower;
    private String token;
    private String hometown;
    private String face_url;
    private String signature;
    private String location;
    private String created_at;
    private String birthday;
    private int following;
    private String career;
    private String username;
    private List<String> tags;
    private double longitude;
    private double latitude;

    public UserData(){}

    public UserData(String birthday, String career, String created_at, String face_url, int follower, int following, String hometown, String location, String username, String phonenumber, String school, int sex, String signature, String token, int uid, String updated_at
    ,List<String> tags,double longitude,double latitude) {
        this.birthday = birthday;
        this.career = career;
        this.created_at = created_at;
        this.face_url = face_url;
        this.follower = follower;
        this.following = following;
        this.hometown = hometown;
        this.location = location;
        this.username = username;
        this.phonenumber = phonenumber;
        this.school = school;
        this.sex = sex;
        this.signature = signature;
        this.token = token;
        this.uid = uid;
        this.updated_at = updated_at;
        this.tags=tags;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFace_url() {
        return face_url;
    }

    public void setFace_url(String face_url) {
        this.face_url = face_url;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "birthday='" + birthday + '\'' +
                ", uid=" + uid +
                ", sex=" + sex +
                ", school='" + school + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", follower=" + follower +
                ", token='" + token + '\'' +
                ", hometown='" + hometown + '\'' +
                ", face_url='" + face_url + '\'' +
                ", signature='" + signature + '\'' +
                ", location='" + location + '\'' +
                ", created_at='" + created_at + '\'' +
                ", following=" + following +
                ", career='" + career + '\'' +
                ", username='" + username + '\'' +
                ", tags=" + tags +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
