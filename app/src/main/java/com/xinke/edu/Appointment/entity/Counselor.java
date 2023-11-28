package com.xinke.edu.Appointment.entity;

public class Counselor {

//    取消原因
    String cancelReason;
//    关联到特定教室的标识符。这个字段应该与教室信息表相关联
    String classroomId;
//    预约创建的时间
    String creationTime;
//    用户的真实姓名
    String fullName;
//    预计参与预约活动的人数
    int numberParticipants;
//    时间段
    String period;
//    预约教室的目的或活动的简短描述
    String purpose;
//    主键
    int reservationId;
//    预约状态（0待审核，1已通过，2已取消）
    int status;
//    预约的具体时间
    String time;
//    最后一次修改预约信息的时间
    String updateTime;
//    预约教室的用户的唯一标识符。这个字段应该与用户信息表相关联




    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNumberParticipants() {
        return numberParticipants;
    }

    public void setNumberParticipants(int numberParticipants) {
        this.numberParticipants = numberParticipants;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}

