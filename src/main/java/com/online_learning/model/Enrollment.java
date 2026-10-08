package com.online_learning.model;

public class Enrollment {

    private int enrollment_id;
    private int student_id;
    private int course_id;
    private String enroll_date;
    private String status;

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getEnroll_date() {
        return enroll_date;
    }

    public void setEnroll_date(String enroll_date) {
        this.enroll_date = enroll_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}