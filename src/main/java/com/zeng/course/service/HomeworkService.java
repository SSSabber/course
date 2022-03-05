package com.zeng.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.CourseMapper;
import com.zeng.course.dao.HomeworkMapper;
import com.zeng.course.model.Course;
import com.zeng.course.model.File_download;
import com.zeng.course.model.Homework;
import com.zeng.course.model.Homework_upload;
import com.zeng.course.util.PageRequest;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HomeworkService {
	@Resource
	private HomeworkMapper homeworkMapper;


	public List<Homework> selectHomeworkByCurseId(Integer courseId) {
		return homeworkMapper.selectHomeworkByCourseId(courseId);
	}

	public void uploadHomework(Integer homeworkId, Integer id, String pathName) {
		homeworkMapper.uploadHomework(homeworkId,id,pathName);
	}

	public Homework_upload selectHomeworkUpload(Integer homeworkId, Integer studentId) {
		return homeworkMapper.selectHomeworkUpload(homeworkId, studentId);
	}

	public PageResult selectHomeworkUploads(int pageNum,int pageSize,int studentId){
		PageHelper.startPage(pageNum, pageSize);
		List<Homework_upload> sysMenus = homeworkMapper.selectHomeworkUploadByStudentId(studentId);
		PageInfo<Homework_upload> pageInfo=new PageInfo<>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	public PageResult selectHomeworkUploadsByCurseIds(int pageNum,int pageSize,List<Integer> courseIds) {
		PageHelper.startPage(pageNum, pageSize);
		List<Homework_upload> sysMenus=new ArrayList<>();
		for(Integer courseId:courseIds){
			List<Homework_upload> homeworkList = homeworkMapper.selectUploadsByCourseId(courseId);
			sysMenus.addAll(homeworkList);
		}
		PageInfo<Homework_upload> pageInfo=new PageInfo<>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	public void editScore(Integer id, String score) {
		homeworkMapper.updateScore(id,score);
	}
}
