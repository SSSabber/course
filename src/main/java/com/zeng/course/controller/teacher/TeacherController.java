package com.zeng.course.controller.teacher;

import com.zeng.course.model.Teacher;
import com.zeng.course.model.Teacher;
import com.zeng.course.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    
    @Autowired
    TeacherService teacherService;

    @RequestMapping("/index")
    public String teacherIndex(){
        return "teacher/teacher-index";
    }
   

    @GetMapping("/edit")
    public String edit(){
        return "teacher/teacher-edit";
    }

    @PostMapping("/edit/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Teacher teacher =(Teacher)request.getSession().getAttribute("teacherUser");
        if(!teacher.getPassword().equals(originalPassword)){
            return "旧密码输入错误";
        }
        teacher.setPassword(newPassword);
        if (teacherService.updateTeacherByModel(teacher)!=0) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("teacherUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/edit/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("username") String username,
                             @RequestParam("name") String name, @RequestParam("tel")String tel) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(name)||StringUtils.isEmpty(tel)) {
            return "参数不能为空";
        }
        Teacher teacher=(Teacher)request.getSession().getAttribute("teacherUser");
        if(username.equals(teacher.getUsername())&&name.equals(teacher.getName())&&tel.equals(teacher.getTel())){
            return "未做修改";
        }
        teacher.setName(name);
        teacher.setUsername(username);
        teacher.setTel(tel);
        if (teacherService.updateTeacherByModel(teacher)!=0) {
            request.getSession().setAttribute("teacherUser",teacher);
            return "success";
        } else {
            return "修改失败";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("teacherUser");
        request.getSession().removeAttribute("errorMsg");
        return "common/login";
    }

}
