package com.zeng.course.dao;


import com.zeng.course.model.Course;
import com.zeng.course.model.File_download;
import com.zeng.course.model.Homework;
import com.zeng.course.model.Homework_upload;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HomeworkMapper {

    List<Homework> selectHomeworkByCourseId(Integer CourseId);

    void uploadHomework(@Param("homeworkId") Integer homeworkId,@Param("studentId") Integer studentId,@Param("pathName") String pathName);

    Homework_upload selectHomeworkUpload(@Param("homeworkId") Integer homeworkId, @Param("studentId") Integer studentId);

    List<Homework_upload> selectHomeworkUploadByStudentId(int studentId);

    List<Homework_upload> selectUploadsByCourseId(Integer courseId);

    void updateScore(@Param("id") Integer id, @Param("score") String score);
}