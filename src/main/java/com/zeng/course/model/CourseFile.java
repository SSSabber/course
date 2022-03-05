package com.zeng.course.model;

import java.util.Date;

public class CourseFile {
    private Integer id;
    private Integer sectionId;
    private String name;
    private String intro=" ";
    private Date uploadTime;
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "CourseFile{" +
                "id=" + id +
                ", sectionId=" + sectionId +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", uploadTime=" + uploadTime +
                ", path='" + path + '\'' +
                '}';
    }
}
