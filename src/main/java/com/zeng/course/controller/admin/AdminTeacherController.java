package com.zeng.course.controller.admin;


import com.zeng.course.model.Teacher;
import com.zeng.course.service.TeacherService;
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
public class AdminTeacherController {
    @Autowired
    TeacherService teacherService;
    

    /**
     *管理教师主页
     */
    @GetMapping("/manage-teacher")
    public String selectTeacher(Model model,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult = teacherService.selectTeacherByPage(pageNum, pageSize);
        model.addAttribute("collegeId", 0);
        model.addAttribute("name", "教师姓名");
        model.addAttribute("pageResult", pageResult);
        return "admin/admin-manage-teacher";
    }

    /**
     *根据参数筛选教师
     */
    @GetMapping("/manage-teacher/selectByParams")
    public String selectTeacherByParams(Model model,
                                        @RequestParam(value = "collegeId", defaultValue = "0") Integer collegeId,
                                        @RequestParam(value = "name", defaultValue = "教师姓名") String name,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult;
        pageResult = teacherService.selectTeacherByParams(pageNum, pageSize, collegeId, name);
        model.addAttribute("collegeId", collegeId);
        model.addAttribute("name", name);
        model.addAttribute("pageResult", pageResult);
        return "admin/admin-manage-teacher";
    }

    /*
      批量删除教师
     */
    @PostMapping("manage-teacher/delete")
    public void deleteTeacher(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<Integer> ids = (List<Integer>) map.get("ids");
        teacherService.deleteTeacherByIds(ids);
    }

    /*
    跳转编辑页面
     */
    @RequestMapping("manage-teacher/edit")
    public String editTeacher(Model model,String teacherNum) {
        Teacher teacher=teacherService.selectTeacherByTeacherNum(teacherNum);
        model.addAttribute("teacher",teacher);
        return "admin/admin-teacher-edit";
    }

    /*
    更新教师
     */
    @PostMapping("manage-teacher/edit")
    public void editTeacher(@RequestBody Map<String, Object> map,HttpServletResponse response){
        teacherService.updateTeacher(map);
    }

    /*
    跳转添加教师页面
     */
    @GetMapping("manage-teacher/insert")
    public String insertTeacher(){
        return "admin/admin-teacher-insert";
    }

    /*
    添加教师
     */
    @PostMapping("manage-teacher/insert")
    public void insertTeacher(@RequestBody Map<String, Object> map,HttpServletResponse response){
        teacherService.insertTeacher(map);
    }

}
