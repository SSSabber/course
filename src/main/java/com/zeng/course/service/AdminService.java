package com.zeng.course.service;

import com.zeng.course.dao.AdminMapper;
import com.zeng.course.model.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {
    @Resource
    private AdminMapper adminMapper;

    public Admin login(String username,String password){
        Admin admin=adminMapper.login(username,password);
        return admin;
    }

//    public boolean updatePassword(Admin admin,String newPassword) {
//        admin.setPassword(newPassword);
//        if(adminMapper.updateAdmin(admin)==1) return true;
//        else return false;
//    }
//
//    public boolean updateName(Admin admin, String name, String username) {
//        admin.setName(name);
//        admin.setUsername(username);
//        if(adminMapper.updateAdmin(admin)==1) return true;
//        else return false;
//    }

    public boolean updateAdmin(Admin admin) {
        if(adminMapper.updateAdmin(admin)==1) return true;
        else return false;
    }
}
