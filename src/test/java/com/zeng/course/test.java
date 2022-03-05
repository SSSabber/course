package com.zeng.course;


import com.zeng.course.dao.AdminMapper;
import com.zeng.course.dao.CourseMapper;
import com.zeng.course.dao.FileMapper;
import com.zeng.course.dao.NoticeMapper;
import com.zeng.course.model.*;
import com.zeng.course.service.*;
import com.zeng.course.util.PageResult;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    StudentService studentService;
    @Autowired
    AdminService adminService;
    @Resource
    NoticeMapper noticeMapper;
    @Resource
    AdminMapper adminMapper;
    @Resource
    FileMapper fileMapper;
    @Autowired
    FileService fileService;

    @Test
    public void TestStudent(){
//        List<Student> students=studentService.selectStudent();
//        for(int i=0;i<students.size();i++){
//            System.out.println(students.get(i));
//        }
//        List<Notice> notice=noticeMapper.selectNotice();
//        for(int i=0;i<notice.size();i++){
//            System.out.println(notice.get(i));
//        }
//        Admin admin=adminService.login("11111","11111");
//        System.out.println(admin);
        List<File_download> file_downloads=fileMapper.selectFileDownloadByStudentId(1);
        System.out.println(file_downloads);

    }

    @Test
    public void test() throws IOException, ParseException, InvalidTokenOffsetsException {
        FileService fileService=new FileService();
        PageResult pageResult=fileService.searchSource(1,10,"概述");
        System.out.println(pageResult);
    }

    @Test
    public void testSelectCourse(){
        List<SearchModel> searchModelList=fileService.searchModelList();
        for(SearchModel searchModel:searchModelList){
            System.out.println(searchModel.toString());
        }
    }

}
