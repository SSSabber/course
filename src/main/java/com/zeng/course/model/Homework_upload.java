package com.zeng.course.model;

import java.util.Date;

public class Homework_upload {
    private int id;
    private String student;
    private Homework homework;
    private Date uploadTime;
    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Homework_upload{" +
                "id=" + id +
                ", student='" + student + '\'' +
                ", homework=" + homework +
                ", uploadTime=" + uploadTime +
                ", score=" + score +
                '}';
    }
}
