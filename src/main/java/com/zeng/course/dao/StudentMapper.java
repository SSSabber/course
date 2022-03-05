package com.zeng.course.dao;


import java.util.List;
import java.util.Map;

import com.zeng.course.model.Student;
import com.zeng.course.model.Teacher;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

    Student login(@Param("username") String username, @Param("password") String password);
    List<Student> selectStudent();
    Student selectStudentById(Integer id);
    List<Student> selectStudentByParams(@Param("collegeId") int  collegeId,@Param("name") String name);
    int selectCount();
    int deleteStudentByIds(List list);
    int insertStudent(Map map);
    int updateStudent(Map map);
    int updateStudentByModel(Map map);
    Student selectStudentByStuNum(String stuNum);

    List<Integer> selectStudentFollowId(Integer teacherId);

}