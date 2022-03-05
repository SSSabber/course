package com.zeng.course.controller.admin;

import com.zeng.course.model.Course;
import com.zeng.course.service.CourseService;
import com.zeng.course.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {
    @Autowired
    CourseService courseService;

    /**
     *管理课程主页
     */
    @GetMapping("/manage-course")
    public String selectCourse(Model model,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult = courseService.selectCourseByPage(pageNum, pageSize);
        model.addAttribute("collegeId", 0);
        model.addAttribute("pageResult", pageResult);
        return "admin-manage-notice";
    }

    /**
     *根据参数筛选课程
     */
    @GetMapping("/manage-course/selectByParams")
    public String selectCourseByParams(Model model,
                                        @RequestParam(value = "collegeId", defaultValue = "0") Integer collegeId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult;
        pageResult = courseService.selectCourseByParams(pageNum, pageSize, collegeId);
        model.addAttribute("collegeId", collegeId);
        model.addAttribute("pageResult", pageResult);
        return "admin-manage-notice";
    }

    /*
      批量删除课程
     */
    @PostMapping("manage-course/delete")
    public String deleteCourse(@RequestBody Map<String, Object> map) {
        List<String> ids = (List<String>) map.get("ids");
        courseService.deleteCourseByIds(ids);
        return "admin-manage-notice";
    }

    /*
    跳转编辑页面
     */
    @RequestMapping("manage-course/edit")
    public String editCourse(Model model,Integer id) {
        Course course=courseService.selectCourseById(id);
        model.addAttribute("course",course);
        return "admin-notice-edit";
    }

    /*
    更新课程
     */
    @PostMapping("manage-course/edit")
    public void editCourse(@RequestBody Map<String, Object> map,HttpServletResponse response){
        courseService.updateCourse(map);
    }

    /*
    跳转添加课程页面
     */
    @GetMapping("manage-course/insert")
    public String insertCourse(){
        return "admin-notice-insert";
    }

    /*
    添加课程
     */
    @PostMapping("manage-course/insert")
    public void insertCourse(@RequestBody Map<String, Object> map,HttpServletResponse response){
        courseService.insertCourse(map);
    }

}
