package com.zeng.course.controller.teacher;

import com.zeng.course.model.Student;
import com.zeng.course.model.Teacher;
import com.zeng.course.service.StudentService;
import com.zeng.course.service.TeacherService;
import com.zeng.course.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherStudentController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;

    @RequestMapping("/student")
    public String selectCourse(Model model,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                               HttpSession session) {
        Teacher teacher=(Teacher)session.getAttribute("teacherUser");
        PageResult pageResult = studentService.selectStudentFollowByPage(pageNum,pageSize,teacher.getId());
        model.addAttribute("pageResult", pageResult);
        return "teacher/teacher-student";
    }

    /*
      添加学生
     */
    @PostMapping("/student/insert")
    @ResponseBody
    public String insertTeacherFollow(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response) {
        String stuId=(String)map.get("stuId");
        Student student=studentService.selectStudentByStuNum(stuId);
        Teacher teacher=(Teacher)session.getAttribute("teacherUser");
        if(teacherService.isFollow(teacher.getId(),student.getId())){
           return "该学生已关注";
        }
        teacherService.insertTeacherFollow(teacher.getId(),student.getId());
        return "添加成功";
    }

    /*
      批量移除关注
     */
    @PostMapping("student/delete")
    public void deleteTeacherFollow(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response) {
        List<String> ids = (List<String>) map.get("ids");
        Teacher teacher=(Teacher)session.getAttribute("teacherUser");
        for(int i=0;i<ids.size();i++){
            int studentId=Integer.parseInt(ids.get(i));
            teacherService.deleteTeacherFollow(studentId,(int)teacher.getId());
        }
    }
}
