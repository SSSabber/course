package com.zeng.course.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.StudentMapper;
import com.zeng.course.model.Student;
import com.zeng.course.model.Teacher;
import com.zeng.course.util.PageRequest;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Resource
	private StudentMapper studentMapper;
	@Autowired
	PageRequest pageRequest;

	/*
	学生登录
	 */
	public Student login(String username,String password){
		Student student=studentMapper.login(username,password);
		return student;
	}

	/**
	 * 分页查询
	 */
	public PageResult selectStudentByPage(int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Student> sysMenus = studentMapper.selectStudent();
		PageInfo<Student> pageInfo=new PageInfo<Student>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	/**
	 * 根据条件查询
	 */
	public PageResult selectStudentByParams(int pageNum,int pageSize,int collegeId,String name){
		PageHelper.startPage(pageNum, pageSize);

		List<Student> sysMenus = studentMapper.selectStudentByParams(collegeId,name);
		PageInfo<Student> pageInfo=new PageInfo<Student>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}
	/**
	 * 根据stuNum查询学生
	 */
	public Student selectStudentByStuNum(String stuNum){
		return studentMapper.selectStudentByStuNum(stuNum);
	}

	/**
	 * 根据id删除
	 */
	public int deleteStudentByIds(List ids){
		return studentMapper.deleteStudentByIds(ids);
	}

	/**
	 * 添加学生
	 */
	public int insertStudent(Map map){
		Map resmap=new HashMap();
		resmap.put("name",map.get("name"));
		resmap.put("stuNum",map.get("stuNum"));
		resmap.put("collegeId",map.get("collegeId"));
		resmap.put("gender",map.get("gender"));
		resmap.put("tel",map.get("tel"));
		resmap.put("username",map.get("username"));
		resmap.put("password",map.get("password"));
		return studentMapper.insertStudent(resmap);
	}

	public int updateStudent(Map map) {
		Map resmap=new HashMap();
		resmap.put("id",map.get("id"));
		resmap.put("name",map.get("name"));
		resmap.put("stuNum",map.get("stuNum"));
		resmap.put("collegeId",map.get("collegeId"));
		resmap.put("gender",map.get("gender"));
		resmap.put("tel",map.get("tel"));
		resmap.put("username",map.get("username"));
		resmap.put("password",map.get("password"));
		return studentMapper.updateStudent(resmap);
	}
	public int updateStudentByModel(Student student) {
		Map resmap=new HashMap();
		resmap.put("id",student.getId());
		resmap.put("name",student.getName());
		resmap.put("stuNum",student.getStuNum());
		resmap.put("tel",student.getTel());
		resmap.put("username",student.getUsername());
		resmap.put("password",student.getPassword());
		return studentMapper.updateStudentByModel(resmap);
	}

	/**
	 *
	 * 查询关注的学生
	 */
	public PageResult selectStudentFollowByPage(Integer pageNum, Integer pageSize, Integer teacherId) {
		PageHelper.startPage(pageNum, pageSize);
		List<Integer> ids=studentMapper.selectStudentFollowId(teacherId);
		List<Student> sysMenus=new LinkedList<>();
		for(int i=0;i<ids.size();i++){
			sysMenus.add(studentMapper.selectStudentById(ids.get(i)));
		}
		PageInfo<Student> pageInfo=new PageInfo<>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

}
