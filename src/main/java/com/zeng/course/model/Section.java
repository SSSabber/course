package com.zeng.course.model;

import java.util.List;

public class Section {
    private Integer id;
    private Integer courseId;
    private String name;
    private List<CourseFile> courseFiles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseFile> getCourseFiles() {
        return courseFiles;
    }

    public void setCourseFiles(List<CourseFile> courseFiles) {
        this.courseFiles = courseFiles;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", name='" + name + '\'' +
                ", courseFiles=" + courseFiles +
                '}';
    }
}
