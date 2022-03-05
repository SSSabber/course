package com.zeng.course.dao;


import com.zeng.course.model.Course;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

public interface CourseMapper {

    List<Course> selectCourse();
    List<Course> selectCourseByParams(@Param("collegeId") int collegeId);
    List<Course> selectCourseLimit(int num);
    int deleteCourseByIds(List list);
    int insertCourse(Map map);
    int updateCourse(Map map);
    Course selectCourseById(Integer id);

    Integer selectTeacherIdByCourseId(Integer id);

    Integer isCourseFollow(@Param("courseId")int courseId, @Param("studentId")int studentId);

    List<Integer> selectCourseFollowId(@Param("studentId")int studentId);
    Integer insertCourseFollow(@Param("courseId")int courseId, @Param("studentId")int studentId);
    Integer deleteCourseFollow(@Param("courseId")int courseId, @Param("studentId")int studentId);

    List<Integer> selectCourseIdByTeacherId(@Param("teacherId")int teacherId);
}