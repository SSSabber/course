package com.zeng.course.controller.admin;


import com.zeng.course.model.Notice;
import com.zeng.course.service.NoticeService;
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
public class AdminNoticeController {
    @Autowired
    NoticeService noticeService;
    

    /**
     *管理公告主页
     */
    @GetMapping("/manage-notice")
    public String selectNotice(Model model,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult = noticeService.selectNoticeByPage(pageNum, pageSize);
        model.addAttribute("pageResult", pageResult);
        return "admin/admin-manage-notice";
    }


    /*
      批量删除公告
     */
    @PostMapping("manage-notice/delete")
    public void deleteNotice(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<Integer> ids = (List<Integer>) map.get("ids");
        noticeService.deleteNoticeByIds(ids);
    }

    /*
    跳转编辑页面
     */
    @RequestMapping("manage-notice/edit")
    public String editNotice(Model model,int id) {
        Notice notice=noticeService.selectNoticeById(id);
        model.addAttribute("notice",notice);
        return "admin/admin-notice-edit";
    }

    /*
    更新公告
     */
    @PostMapping("manage-notice/edit")
    public void editNotice(@RequestBody Map<String, Object> map,HttpServletResponse response){
        noticeService.updateNotice(map);
    }

    /*
    跳转添加公告页面
     */
    @GetMapping("manage-notice/insert")
    public String insertNotice(){
        return "admin/admin-notice-insert";
    }

    /*
    添加公告
     */
    @PostMapping("manage-notice/insert")
    public void insertNotice(@RequestBody Map<String, Object> map,HttpServletResponse response){
        noticeService.insertNotice(map);
    }

}
