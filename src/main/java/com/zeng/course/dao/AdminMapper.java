package com.zeng.course.dao;

import com.zeng.course.model.Admin;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;

public interface AdminMapper {

    Admin login(@Param("username") String username, @Param("password") String password);

    int updatePassword(@PathParam("id") Integer id, @PathParam("newPassword") String newPassword);

    int updateName(@PathParam("id")Integer id, @PathParam("name")String name,@PathParam("username")String username);

    int updateAdmin(Admin admin);
}
