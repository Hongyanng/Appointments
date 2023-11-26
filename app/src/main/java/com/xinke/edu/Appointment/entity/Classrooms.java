package com.xinke.edu.Appointment.entity;


/**
 * 选择教室的实体类
 */
public class Classrooms {

    //{"id":1,"classroomId":"C101","classroomType":"亿纬产业学院","capacity":null,"seatingArrangement":"","building":"C","buildingName":"明志楼","floor":1}
    String classroomId;
    String classroomType;
    String capacity;
    String seatingArrangement;
    String building;
    String buildingName;
    int floor;
    String time;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    String period;


    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomType() {
        return classroomType;
    }

    public void setClassroomType(String classroomType) {
        this.classroomType = classroomType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSeatingArrangement() {
        return seatingArrangement;
    }

    public void setSeatingArrangement(String seatingArrangement) {
        this.seatingArrangement = seatingArrangement;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }


}
