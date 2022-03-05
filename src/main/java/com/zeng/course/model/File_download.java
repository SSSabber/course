package com.zeng.course.model;

import java.util.Date;

public class File_download {
    private Integer id;
    private Integer studentId;
    private CourseFile courseFile;
    private Date downloadTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public CourseFile getCourseFile() {
        return courseFile;
    }

    public void setCourseFile(CourseFile courseFile) {
        this.courseFile = courseFile;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    @Override
    public String toString() {
        return "File_download{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseFile=" + courseFile +
                ", downloadTime=" + downloadTime +
                '}';
    }
}
