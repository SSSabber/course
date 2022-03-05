package com.zeng.course.controller.teacher;

import com.sun.deploy.net.HttpResponse;
import com.zeng.course.model.Course;
import com.zeng.course.model.Section;
import com.zeng.course.model.Student;
import com.zeng.course.model.Teacher;
import com.zeng.course.service.*;
import com.zeng.course.util.PageResult;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherCourseController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    SectionService sectionService;
    @Autowired
    FileService fileService;
    @Value("${file.upload.url}")
    private String fileUploadPath;

    @RequestMapping("/course")
    public String selectCourse(Model model,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                               HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        PageResult pageResult = courseService.selectCourseByTeacherId(pageNum, pageSize, teacher.getId());
        model.addAttribute("pageResult", pageResult);
        return "teacher/teacher-course";
    }

    /*
      添加课程
     */
    @PostMapping("/course/insert")
    @ResponseBody
    public String insertTeacherFollow(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response) {
        String courseName = (String) map.get("courseName");
        String courseIntro = (String) map.get("courseIntro");
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        Map resMap = new HashMap();
        resMap.put("name", courseName);
        resMap.put("intro", courseIntro);
        resMap.put("teacherId", teacher.getId());
        courseService.insertCourse(resMap);
        return "添加成功";
    }

    /*
      批量删除课程
     */
    @PostMapping("/course/delete")
    public void deleteCourse(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<String> ids = (List<String>) map.get("ids");
        courseService.deleteCourseByIds(ids);
    }

    /**
     * 跳转课程详情页面
     */
    @GetMapping("/course/goEdit")
    public String editCoursePage(@RequestParam("courseId") int courseId, Model model) {
        Course course = courseService.selectCourseById(courseId);
        List<Section> sections = sectionService.selectSectionByCourseId(courseId);
        model.addAttribute("course", course);
        model.addAttribute("sections", sections);
        return "teacher/course-edit";

    }

    /**
     * 修改课程
     */
    @PostMapping("/course/edit")
    public void editCourse(@RequestBody() Map<String, Object> map, HttpServletResponse response, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacherUser");
        Integer courseId = (Integer) map.get("courseId");
        String courseName = (String) map.get("courseName");
        String courseIntro = (String) map.get("courseIntro");
        Map resMap = new HashMap();
        resMap.put("id", courseId);
        resMap.put("name", courseName);
        resMap.put("intro", courseIntro);
        resMap.put("teacherId", teacher.getId());
        courseService.updateCourse(resMap);
    }


    /**
     * 课程添加章节
     */
    @PostMapping("/section/insertSection")
    public void insertSection(@RequestBody Map<String, Object> map, HttpServletResponse response){
        sectionService.insertSection(map);
    }

    /**
     * 修改章节名称
     */
    @PostMapping("/section/editSection")
    public void editSection(@RequestBody Map<String,Object> map,HttpServletResponse response){
        Integer sectionId=(Integer)map.get("sectionId");
        String sectionName=(String)map.get("sectionName");
        sectionService.editSection(sectionId,sectionName);
    }

    /**
     * 删除章节
     */
    @PostMapping("/section/deleteSection")
    public void deleteSection(@RequestBody Map<String,Object> map,HttpServletResponse response){
        Integer sectionId= (Integer) map.get("sectionId");
        sectionService.deleteSection(sectionId);
    }

    /**
     * 删除资源
     */
    @PostMapping("/file/deleteFile")
    public void deleteFile(@RequestBody Map<String,Object> map,HttpServletResponse response){
        List<Integer> list=new LinkedList<>();
        list.add((Integer)map.get("fileId"));
        fileService.deleteFileByIds(list);
    }

    /**
     * 上传资源
     */
    @RequestMapping("/file/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam("courseId")Integer courseId,
                             @RequestParam(value = "fId",required = false) Integer fId,
                             @RequestParam("sectionId")Integer sectionId,
                             @RequestParam("fName")String name,
                             @RequestParam("fIntro")String intro,
                             @RequestParam("fFile")MultipartFile file,
                             HttpServletRequest request,
                             HttpServletResponse response){
        //获取文件名
        String fileName = file.getOriginalFilename();
        //新建File文件，参数为上传文件的目标文件夹
        File dir = new File(fileUploadPath + "/course"+courseId);
        //如果dir的路径不存在则创建新的文件加
        if(!dir.exists()){
            dir.mkdir();
        }
        //上传文件的路径
        String pathName=fileUploadPath + "/course"+courseId+"/"+fileName;
        File dest=new File(pathName);
        try {
            //进行文件传输
            file.transferTo(dest);
            if(fId==null){
                fileService.insertFile(sectionId,name,intro,pathName);
            }else{
                fileService.uploadFile(fId,sectionId,name,intro,pathName);
            }
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "上传失败";
    }

}
