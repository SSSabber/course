package com.zeng.course.controller.course;

import com.zeng.course.model.*;
import com.zeng.course.service.*;
import com.zeng.course.util.PageResult;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseControlller {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SectionService sectionService;
    @Autowired
    FileService fileService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    IndexService indexService;
    @Value("${file.upload.url}")
    private String fileUploadPath;


    /*
    课程列表
     */
    @GetMapping("/list")
    public String courseList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                             @RequestParam(value = "collegeId",defaultValue = "0")Integer collegeId,
                             Model model){
        PageResult pageResult;
        if(collegeId==0){
            pageResult=courseService.selectCourseByPage(pageNum,pageSize);
        }else{
            pageResult=courseService.selectCourseByParams(pageNum,pageSize,collegeId);
        }
        model.addAttribute("collegeId",collegeId);
        model.addAttribute("pageResult",pageResult);
        return "common/course-list";
    }

    /*
    教师的课程列表
     */
    @GetMapping("/teacherList")
    public String courseTeacherList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                             @RequestParam(value = "teacherId")Integer teacherId,
                             @RequestParam(value="teacherName")String teacherName,
                             Model model){
        PageResult pageResult=courseService.selectCourseByTeacherId(pageNum,pageSize,teacherId);
        model.addAttribute("pageResult",pageResult);
        model.addAttribute("teacherName",teacherName);
        return "common/course-teacher-list";
    }

    /*
    课程的学习页面
     */
    @GetMapping("/learn")
    public String courseLearn(Model model,
                              @RequestParam(value = "id",required =true)Integer id, HttpSession session){
        Course course=courseService.selectCourseById(id);
        int teacherId=courseService.selectTeacherIdByCourseId(id);
        Teacher teacher=teacherService.selectTeacherById(teacherId);
        List<Section> sections=sectionService.selectSectionByCourseId(id);
        List<Homework> homeworks=homeworkService.selectHomeworkByCurseId(id);

        Student student= (Student) session.getAttribute("studentUser");

        if(courseService.isFollow(id,student.getId())){
            model.addAttribute("collect",1);
        }else{
            model.addAttribute("collect",0);
        }
        if(teacherService.isFollow(teacherId,student.getId())){
            model.addAttribute("follow",1);
        }else{
            model.addAttribute("follow",0);
        }
        model.addAttribute("course",course);
        model.addAttribute("teacher",teacher);
        model.addAttribute("sections",sections);
        model.addAttribute("homeworks",homeworks);
        return "common/course-learn";
    }

    /**
     * 课程资源下载
     */
    @GetMapping("/download")
    @ResponseBody
    public void courseDownLoad(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam("fileId") Integer fileId,
                                 @RequestParam("path")String path,
                                 @RequestParam("fileName")String fileName) throws IOException {
        if(path!=null){
            File file=new File(path);
            if(file.exists()){
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename="
                                                           + java.net.URLEncoder.encode(fileName, "UTF-8"));
                byte[] buffer=new byte[1024];
                FileInputStream fis=null;
                BufferedInputStream bis=null;
                try{
                    fis=new FileInputStream(file);
                    bis=new BufferedInputStream(fis);
                    OutputStream os=response.getOutputStream();
                    int i=bis.read(buffer);
                    while(i!=-1){
                        os.write(buffer,0,1);
                        i=bis.read(buffer);
                    }
                    Student student= (Student) request.getSession().getAttribute("studentUser");
                    if(student!=null){
                        fileService.insertFileDownload(student.getId(),fileId);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    bis.close();
                    fis.close();
                }
            }
        }
    }

    /**
     * 收藏课程
     */
    @PostMapping("/doCourseFollow")
    public void doCourseFollow(@RequestParam("courseId")int courseId,
                               HttpSession session,HttpServletResponse response){
        //通过session对象得到登录的Student对象
        Student student= (Student) session.getAttribute("studentUser");
        //判断学生是否收藏过该课程
        if(courseService.isFollow(courseId,student.getId())){
            //调用CourseService中的方法取消课程收藏
            courseService.deleteCourseFollow(courseId,student.getId());
        }else{
            //调用CourseService中的方法添加课程收藏
            courseService.insertCourseFollow(courseId,student.getId());
        }
    }

    /**
     * 关注老师
     */
    @PostMapping("/doTeacherFollow")
    public void doTeacherFollow(@RequestParam("teacherId")int teacherId,HttpSession session,HttpServletResponse response){
        Student student= (Student) session.getAttribute("studentUser");
        if(teacherService.isFollow(teacherId,student.getId())){
            teacherService.deleteTeacherFollow(teacherId,student.getId());
        }else{
            teacherService.insertTeacherFollow(teacherId,student.getId());
        }
    }

    /**
     * 上传作业
     */
    @RequestMapping("/homework/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("homeworkId")Integer homeworkId,
                             @RequestParam("courseId") Integer courseId,
                             @RequestParam("file")MultipartFile file,
                             HttpServletRequest request, HttpServletResponse response){
        Student student= (Student) request.getSession().getAttribute("studentUser");
        String fileName = file.getOriginalFilename();
        if(fileName.equals("")) return "请选择文件";
        File dir = new File(fileUploadPath + "/homework"+courseId);
        if(!dir.exists()){
            dir.mkdir();
        }
        String pathName=fileUploadPath + "/homework"+courseId+"/"+fileName;
        File dest=new File(pathName);
        try {
            file.transferTo(dest);
            Homework_upload homework_upload=homeworkService.selectHomeworkUpload(homeworkId,student.getId());
            if(homework_upload!=null){
                return "请勿重复上交。上次上交的时间为:"+homework_upload.getUploadTime();
            }
            else{
                homeworkService.uploadHomework(homeworkId,student.getId(),pathName);
                return "上交成功";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上交失败";
    }

    /**
     * 搜索资源
     */
    @GetMapping("/search")
    public String selectSource(Model model,
                               @RequestParam(value = "queryString",defaultValue = " ") String queryString,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) throws IOException, ParseException, InvalidTokenOffsetsException {
        PageResult pageResult;
        pageResult = fileService.searchSource(pageNum, pageSize, queryString);
        model.addAttribute("queryString",queryString);
        model.addAttribute("pageResult", pageResult);
        return "common/course-search";
    }

    /**
     * 更新索引
     */
    @GetMapping("/updateIndex")
    @ResponseBody
    public String updateIndex() throws IOException {
        indexService.deleteAllIndex();
        indexService.createIndex();
        return "更新成功";
    }
}
