package com.zeng.course.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class UploadController {

    @Value("${file.upload.url}")
    private String fileUploadPath;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam(value = "courseId",defaultValue = "1")Integer courseId) {
        if (file.isEmpty()) {
            return "上传失败";
        }

        String fileName = file.getOriginalFilename();

        File dir = new File(fileUploadPath + "/course"+courseId);
        if(!dir.exists()){
            dir.mkdir();
        }
        File dest=new File(fileUploadPath + "/course"+courseId+"/"+fileName);
        try {
            file.transferTo(dest);
            return "上传成功，文件地址为：/upload/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功，文件地址为：/upload/" + fileName;
    }
}