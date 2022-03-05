package com.zeng.course.controller.student;

import com.alibaba.fastjson.JSON;
import com.zeng.course.model.Student;
import com.zeng.course.service.CourseService;
import com.zeng.course.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentCourseController {
    @Autowired
    CourseService courseService;

    @RequestMapping("/course")
    public String selectCourse(Model model,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                               HttpSession session) {
        Student student=(Student)session.getAttribute("studentUser");
        PageResult pageResult = courseService.selectCourseFollowByPage(pageNum,pageSize,student.getId());
        model.addAttribute("pageResult", pageResult);
        return "student/student-course";
    }

    /*
      批量取消收藏
     */
    @PostMapping("course/delete")
    public void deleteCourseFollow(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response) {
        List<String> ids = (List<String>) map.get("ids");
        Student student=(Student)session.getAttribute("studentUser");
        for(int i=0;i<ids.size();i++){
            int courseId=Integer.parseInt(ids.get(i));
            courseService.deleteCourseFollow(courseId,(int)student.getId());
        }
    }
}
