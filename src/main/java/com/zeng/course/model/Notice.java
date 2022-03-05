package com.zeng.course.model;

import java.util.Date;

public class Notice {
    private Integer id;
    private String title;
    private String content;
    //private Date record_time;
    private Date record_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }
//    public Date getRecord_time() {
//        return record_time;
//    }
//
//    public void setRecord_time(Date record_time) {
//        this.record_time = record_time;
//    }

//    @Override
//    public String toString() {
//        return "notice{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", record_time=" + record_time +
//                '}';
//    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", record_time='" + record_time + '\'' +
                '}';
    }
}
