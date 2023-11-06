package com.xinke.edu.Appointment.entity;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;

public class User {
//    用户ID（User ID）：通常是整数，可以使用整数类型，如INT。
int User_ID;
//    用户名（Username）：字符串，VARCHAR 或 TEXT。
String user_name;
//    姓名（Full Name）：字符串，VARCHAR 或 TEXT。
String full_name;
//    电子邮件地址（Email Address）：字符串，VARCHAR 或 TEXT。
String email_address;
//    密码（Password）：通常是经过加密的字符串，使用哈希值存储，常见的数据类型是VARCHAR 或 BINARY。
String password;
//    手机号码（Phone Number）：字符串，VARCHAR 或 CHAR，以及可能的格式验证。
String phone_number;
//    角色（Role）：字符串，VARCHAR 或 ENUM。
String role;
//    头像（Avatar）：可以是图像文件的存储路径或二进制数据，通常使用VARCHAR 或 BLOB。
String avatar;
//    班级/课程（Class/Course）：字符串，VARCHAR 或 TEXT。
String course;
//    地址（Address）：字符串，VARCHAR 或 TEXT。
String address;
//    性别（Gender）：字符串，VARCHAR 或 ENUM。
String gender;
//    生日（Date of Birth）：日期类型，如DATE。
String birth;
//    注册日期（Registration Date）：日期时间类型，如DATETIME。
SimpleDateFormat registration_date;
//    最后登录时间（Last Login Time）：日期时间类型，如DATETIME。
SimpleDateFormat last_login_time;
//    认证状态（Authentication Status）：整数或布尔值，以表示认证状态。
int authentication_status;
//    邮箱验证状态（Email Verification Status）：整数或布尔值，以表示验证状态。
int email_verification_status;
//    手机号码验证状态（Phone Number Verification Status）：整数或布尔值，以表示验证状态。
int phone_number_verification_status;
String school;

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public SimpleDateFormat getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(SimpleDateFormat registration_date) {
        this.registration_date = registration_date;
    }

    public SimpleDateFormat getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(SimpleDateFormat last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getAuthentication_status() {
        return authentication_status;
    }

    public void setAuthentication_status(int authentication_status) {
        this.authentication_status = authentication_status;
    }

    public int getEmail_verification_status() {
        return email_verification_status;
    }

    public void setEmail_verification_status(int email_verification_status) {
        this.email_verification_status = email_verification_status;
    }

    public int getPhone_number_verification_status() {
        return phone_number_verification_status;
    }

    public void setPhone_number_verification_status(int phone_number_verification_status) {
        this.phone_number_verification_status = phone_number_verification_status;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
