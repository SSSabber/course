package com.zeng.course.controller.student;

import com.zeng.course.model.Homework_upload;
import com.zeng.course.model.Student;
import com.zeng.course.service.FileService;
import com.zeng.course.service.HomeworkService;
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
@RequestMapping("/student")
public class StudentHomeworkController {
    @Autowired
    HomeworkService homeworkService;

    @GetMapping("/homework")
    public String studentHomework(Model model,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                                  HttpSession session) {
        Student student = (Student) session.getAttribute("studentUser");
        PageResult pageResult = homeworkService.selectHomeworkUploads(pageNum, pageSize, student.getId());
        model.addAttribute("pageResult", pageResult);
        return "student/student-homework";
    }
}
