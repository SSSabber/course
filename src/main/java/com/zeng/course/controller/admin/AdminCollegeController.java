package com.zeng.course.controller.admin;


import com.zeng.course.model.College;
import com.zeng.course.service.CollegeService;
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
public class AdminCollegeController {
    @Autowired
    CollegeService collegeService;


    /**
     *管理学院主页
     */
    @GetMapping("/manage-college")
    public String selectCollege(Model model,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageResult pageResult = collegeService.selectCollegeByPage(pageNum, pageSize);
        model.addAttribute("pageResult", pageResult);
        return "admin/admin-manage-college";
    }


    /*
      批量删除学院
     */
    @PostMapping("manage-college/delete")
    public String deleteCollege(@RequestBody Map<String, Object> map, Model model) {
        List<Integer> ids = (List<Integer>) map.get("ids");
        collegeService.deleteCollegeByIds(ids);
        return "admin/admin-manage-college";
    }

    /*
    跳转编辑页面
     */
    @RequestMapping("manage-college/edit")
    public String editCollege(Model model,Integer id) {
        College college=collegeService.selectCollegeById(id);
        model.addAttribute("college",college);
        return "admin/admin-college-edit";
    }

    /*
    更新学院
     */
    @PostMapping("manage-college/edit")
    public void editCollege(@RequestBody Map<String, Object> map,HttpServletResponse response){
        collegeService.updateCollege(map);
    }

    /*
    跳转添加学院页面
     */
    @GetMapping("manage-college/insert")
    public String insertCollege(){
        return "admin/admin-college-insert";
    }

    /*
    添加学院
     */
    @PostMapping("manage-college/insert")
    public void insertCollege(@RequestBody Map<String, Object> map,HttpServletResponse response){
        collegeService.insertCollege(map);
    }

}
