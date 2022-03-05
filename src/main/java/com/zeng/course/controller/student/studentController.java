package com.zeng.course.controller.student;

import com.zeng.course.model.Admin;
import com.zeng.course.model.Student;
import com.zeng.course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class studentController{
    @Autowired
    StudentService studentService;

    @RequestMapping("/index")
    public String index(){
        return "student/student-index";
    }

    @GetMapping("/edit")
    public String edit(){
        return "student/student-edit";
    }

    @PostMapping("/edit/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Student student =(Student)request.getSession().getAttribute("studentUser");
        if(!student.getPassword().equals(originalPassword)){
            return "旧密码输入错误";
        }
        student.setPassword(newPassword);
        if (studentService.updateStudentByModel(student)!=0) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("studentUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/edit/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("username") String username,
                             @RequestParam("name") String name,@RequestParam("tel")String tel) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(name)||StringUtils.isEmpty(tel)) {
            return "参数不能为空";
        }
        Student student=(Student)request.getSession().getAttribute("studentUser");
        if(username.equals(student.getUsername())&&name.equals(student.getName())&&tel.equals(student.getTel())){
            return "未做修改";
        }
        student.setName(name);
        student.setUsername(username);
        student.setTel(tel);
        if (studentService.updateStudentByModel(student)!=0) {
            request.getSession().setAttribute("studentUser",student);
            return "success";
        } else {
            return "修改失败";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("studentUser");
        request.getSession().removeAttribute("errorMsg");
        return "common/login";
    }
}
