package com.xinke.edu.Appointment.entity;

/**
 * 课程表实体类
 */
public class Course {
    int id; //主键
    String courseid;//课程id
    String classroomid;//教室id
    String coursename;//课程名称
    String teachername;//教师姓名
    String time;//时间
    String period;//第几节
    String semester;//学期
    int reservation_id;//预约id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getClassroomid() {
        return classroomid;
    }

    public void setClassroomid(String classroomid) {
        this.classroomid = classroomid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }
}
