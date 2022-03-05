package com.zeng.course.controller.common;

import com.zeng.course.model.*;
import com.zeng.course.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommonController {

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    NoticeService noticeService;

    /**
     * 首页
     */
    @RequestMapping(value = {"/index","/"})
    public String index(Model model){
        List<Notice> notices=noticeService.selectNoticeLimit(17);
        List<Course> courses=courseService.selectCourseLimit(9);
        List<Teacher> teachers=teacherService.selectTeaccherLimit(7);
        model.addAttribute("notices",notices);
        model.addAttribute("courses",courses);
        model.addAttribute("teachers",teachers);
        return "common/index";
    }

    /*
    查看公告
     */
    @GetMapping("/notice")
    public String notice(Model model,@RequestParam("id")int id){
        Notice notice=noticeService.selectNoticeById(id);
        model.addAttribute("notice",notice);
        return "common/notice";
    }

    /*
    登录界面
     */
    @GetMapping("/login")
    public String login() {
        return "common/login";
    }

    /*
    用户登录
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("verifyCode") String verifyCode, @RequestParam("status") String status,
            HttpSession session, Model model) {
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        model.addAttribute("status",status);
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "common/login";
        }
        if(status.equals("0")){
            Student student = studentService.login(username, password);
            if (student != null) {
                session.setAttribute("studentUser",student);
                return "forward:/index";
            } else {
                session.setAttribute("errorMsg", "用户名或密码错误");
                return "common/login";
            }
        }

        if(status.equals("1")){
            Teacher teacher=teacherService.login(username, password);
            if (teacher != null) {
                session.setAttribute("teacherUser",teacher);
                return "teacher/teacher-index";
            } else {
                session.setAttribute("errorMsg", "用户名或密码错误");
                return "common/login";
            }
        }

       else{
            Admin admin = adminService.login(username, password);
            if (admin != null) {
                session.setAttribute("adminUser",admin);
                return "admin/admin-index";
            } else {
                session.setAttribute("errorMsg", "用户名或密码错误");
                return "common/login";
            }
        }
    }


    /*
    注册页面
     */
    @GetMapping("/register")
    public String regisiter(){
        return "common/register";
    }

    /*
    用户注册
     */
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("password2") String password2,
                           @RequestParam("name") String name,
                           @RequestParam("status") String status,
                           @RequestParam("collegeId") String collegeId,
                           @RequestParam("num") String num,
                           @RequestParam("gender") String gender,
                           @RequestParam("tel") String tel,
                           @RequestParam("verifyCode") String verifyCode,
                           HttpSession session,
                           Model model){
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        model.addAttribute("password2",password2);
        model.addAttribute("name",name);
        model.addAttribute("status",status);
        model.addAttribute("collegeId",collegeId);
        model.addAttribute("num",num);
        model.addAttribute("gender",gender);
        model.addAttribute("tel",tel);
        if (!password.equals(password2)) {
            session.setAttribute("errorMsg2", "两次输入的密码不相同");
            return "common/register";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg2", "验证码错误");
            return "common/register";
        }
        Map map=new HashMap();
        map.put("name",name);
        map.put("collegeId",collegeId);
        map.put("gender",gender);
        map.put("tel",tel);
        map.put("username",username);
        map.put("password",password);

        if(status.equals("0")){
            if(studentService.selectStudentByStuNum(num)!=null){
                session.setAttribute("errorMsg2", "学号重复");
                return "common/register";
            }
            map.put("stuNum",num);
            if(studentService.insertStudent(map)!=0){
                
                session.removeAttribute("errorMsg2");
                session.setAttribute("errorMsg","注册成功,请登录");
                return "common/login";
            }
        }else{
            if(teacherService.selectTeacherByTeacherNum(num)!=null){
                session.setAttribute("errorMsg2", "教师编号重复");
                return "common/register";
            }
            map.put("teacherNum",num);
            if(teacherService.insertTeacher(map)!=0){
                session.removeAttribute("errorMsg2");
                session.setAttribute("errorMsg","注册成功,请登录");
                return "common/login";
            }
        }
        session.setAttribute("errorMsg2", "账号重复");
        return "common/register";
    }

}


