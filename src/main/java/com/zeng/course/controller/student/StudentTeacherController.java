package com.zeng.course.controller.student;

import com.zeng.course.model.Student;
import com.zeng.course.service.TeacherService;
import com.zeng.course.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentTeacherController {
    @Autowired
    TeacherService teacherService;

    @RequestMapping("/teacher")
    public String selectCourse(Model model,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                               HttpSession session) {
        Student student=(Student)session.getAttribute("studentUser");
        PageResult pageResult = teacherService.selectTeacherFollowByPage(pageNum,pageSize,student.getId());
        model.addAttribute("pageResult", pageResult);
        return "student/student-teacher";
    }

    /*
      批量取消收藏
     */
    @PostMapping("teacher/delete")
    public void deleteTeacherFollow(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response) {
        List<String> ids = (List<String>) map.get("ids");
        Student student=(Student)session.getAttribute("studentUser");
        for(int i=0;i<ids.size();i++){
            int teacherId=Integer.parseInt(ids.get(i));
            teacherService.deleteTeacherFollow(teacherId,(int)student.getId());
        }
    }
}
