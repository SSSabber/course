package com.zeng.course.model;

public class SearchModel {
    private Course course;
    private Section section;
    private CourseFile courseFile;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public CourseFile getCourseFile() {
        return courseFile;
    }

    public void setCourseFile(CourseFile courseFile) {
        this.courseFile = courseFile;
    }

    @Override
    public String toString() {
        return "SearchModel{" +
                "course=" + course +
                ", section=" + section +
                ", courseFile=" + courseFile +
                '}';
    }
}
