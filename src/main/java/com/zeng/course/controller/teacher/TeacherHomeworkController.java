package com.zeng.course.controller.teacher;

import com.zeng.course.dao.CourseMapper;
import com.zeng.course.model.Homework;
import com.zeng.course.model.Teacher;
import com.zeng.course.service.CourseService;
import com.zeng.course.service.HomeworkService;
import com.zeng.course.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherHomeworkController {
    @Autowired
    CourseService courseService;
    @Autowired
    HomeworkService  homeworkService;
    @Resource
    CourseMapper courseMapper;

    @GetMapping("/homework")
    public String manageHomework(Model model,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                                 HttpSession session) {
        Teacher teacher= (Teacher) session.getAttribute("teacherUser");
        List<Integer> courseIds = courseMapper.selectCourseIdByTeacherId(teacher.getId());
        PageResult pageResult=homeworkService.selectHomeworkUploadsByCurseIds(pageNum,pageSize,courseIds);
        model.addAttribute("pageResult",pageResult);
        return "teacher/teacher-homework";
    }

    @PostMapping("/editScore")
    @ResponseBody
    public String editScore(@RequestBody Map map){
        Integer id= (Integer) map.get("id");
        String score= (String) map.get("score");
        homeworkService.editScore(id,score);
        return "修改成功";
    }
}
