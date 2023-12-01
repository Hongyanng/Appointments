package com.xinke.edu.Appointment.entity;


/**
 * 学生查询预约记录接口的实体类
 */

public class MyReservation {



//    cancelReason	取消原因	string
//    classroomId	关联到特定教室的标识符。这个字段应该与教室信息表相关联	string
//    creationTime	记录预约创建的时间	string(date-time)
//    fullName	用户的真实姓名	string
//    numberParticipants	预计参与预约活动的人数	integer(int32)
//    period	时间段	string
//    purpose	预约教室的目的或活动的简短描述	string
//    reservationId	主键	integer(int32)
//    status	表示预约状态的字段（0待审核，1已通过，2已取消）	integer(int32)
//    time	预约的具体时间	string(date)
//    updateTime	最后一次修改预约信息的时间	string(date-time)
//    userId	预约教室的用户的唯一标识符。这个字段应该与用户信息表相关联	integer(int32)

    private int reservationId;
    private String classroomId;
    private int userId;
    private String fullName;
    private String time;
    private String period;
    private int status;
    private String creationTime;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private String updateTime;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getNumberParticipants() {
        return numberParticipants;
    }

    public void setNumberParticipants(int numberParticipants) {
        this.numberParticipants = numberParticipants;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    private String purpose;
    private int numberParticipants;
    private String cancelReason;

    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
