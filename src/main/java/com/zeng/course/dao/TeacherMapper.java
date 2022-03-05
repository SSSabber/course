package com.zeng.course.dao;


import com.zeng.course.model.Student;
import com.zeng.course.model.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {

    Teacher login(@Param("username") String username, @Param("password") String password);
    List<Teacher> selectTeacher();
    List<Teacher> selectTeacherByParams(@Param("collegeId") int collegeId, @Param("name") String name);
    List<Teacher> selectTeacherLimit(int num);
    int deleteTeacherByIds(List list);
    int insertTeacher(Map map);
    int updateTeacher(Map map);
    int updateTeacherByModel(Map map);
    Teacher selectTeacherByTeacherNum(String teacherNum);
    Teacher selectTeacherById(Integer id);
    Integer selectTeacherFollow(@Param("teacherId") int teacherId,@Param("studentId") int studentId);
    Integer deleteTeacherFollow(@Param("teacherId") int teacherId,@Param("studentId") int studentId);
    Integer insertTeacherFollow(@Param("teacherId") int teacherId,@Param("studentId") int studentId);
    List<Integer> selectTeacherFollowId(@Param("studentId")int studentId);
}