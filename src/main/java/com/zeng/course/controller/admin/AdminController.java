package com.zeng.course.controller.admin;

import com.zeng.course.model.Admin;
import com.zeng.course.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/edit")
    public String edit(){
        return "admin/admin-edit";
    }

    @PostMapping("/edit/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Admin admin=(Admin)request.getSession().getAttribute("adminUser");
        if(!admin.getPassword().equals(originalPassword)){
            return "旧密码输入错误";
        }
        admin.setPassword(newPassword);
        if (adminService.updateAdmin(admin)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("adminUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/edit/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("username") String username,
                             @RequestParam("name") String name) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(name)) {
            return "参数不能为空";
        }
        Admin admin=(Admin)request.getSession().getAttribute("adminUser");
        if(username.equals(admin.getUsername())&&name.equals(admin.getName())){
            return "未做修改";
        }
        admin.setName(name);
        admin.setUsername(username);
        if (adminService.updateAdmin(admin)) {
            request.getSession().setAttribute("adminUser",admin);
            return "success";
        } else {
            return "修改失败";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //移除session中保存的用户信息
        request.getSession().removeAttribute("adminUser");
        //移除session中保存的登录错误信息
        request.getSession().removeAttribute("errorMsg");
        return "common/login";
    }

}
