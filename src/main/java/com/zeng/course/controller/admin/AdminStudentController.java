package com.zeng.course.controller.admin;

import com.zeng.course.model.Student;
import com.zeng.course.service.StudentService;
import com.zeng.course.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminStudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = {"", "/index"})
    public String adminIndex() {
        return "admin/admin-index";
    }

    /**
     *管理学生主页
     */
    @GetMapping("/manage-student")
    public String selectStudent(Model model,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult = studentService.selectStudentByPage(pageNum, pageSize);
        model.addAttribute("collegeId", 0);
        model.addAttribute("name", "学生姓名");
        model.addAttribute("pageResult", pageResult);
        return "admin/admin-manage-student";
    }

    /**
     *根据参数筛选学生
     */
    @GetMapping("/manage-student/selectByParams")
    public String selectStudentByParams(Model model,
                                        @RequestParam(value = "collegeId", defaultValue = "0") Integer collegeId,
                                        @RequestParam(value = "name", defaultValue = "学生姓名") String name,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult;
        pageResult = studentService.selectStudentByParams(pageNum, pageSize, collegeId, name);
        model.addAttribute("collegeId", collegeId);
        model.addAttribute("name", name);
        model.addAttribute("pageResult", pageResult);
        return "admin/admin-manage-student";
    }

    /*
      批量删除学生
     */
    @PostMapping("manage-student/delete")
    public void deleteStudent(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<String> ids = (List<String>) map.get("ids");
        studentService.deleteStudentByIds(ids);
    }

    /*
    跳转编辑页面
     */
    @RequestMapping("manage-student/edit")
    public String editStudent(Model model,String stuNum) {
        Student student=studentService.selectStudentByStuNum(stuNum);
        model.addAttribute("student",student);
        return "admin/admin-student-edit";
    }

    /*
    更新学生
     */
    @PostMapping("manage-student/edit")
    public void editStudent(@RequestBody Map<String, Object> map,HttpServletResponse response){
        studentService.updateStudent(map);
    }

    /*
    跳转添加学生页面
     */
    @GetMapping("manage-student/insert")
    public String insertStudent(){
        return "admin/admin-student-insert";
    }

    /*
    添加学生
     */
    @PostMapping("manage-student/insert")
    public void insertStudent(@RequestBody Map<String, Object> map,HttpServletResponse response){
        studentService.insertStudent(map);
    }

}
