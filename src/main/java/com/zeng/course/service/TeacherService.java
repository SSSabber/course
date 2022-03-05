package com.zeng.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.TeacherMapper;
import com.zeng.course.model.Course;
import com.zeng.course.model.Student;
import com.zeng.course.model.Teacher;
import com.zeng.course.util.PageRequest;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
	@Resource
	private TeacherMapper teacherMapper;
	@Autowired
	PageRequest pageRequest;

	/*
	教师登录
	 */
	public Teacher login(String username,String password){
		Teacher teacher=teacherMapper.login(username,password);
		return teacher;
	}


	/**
	 * 分页查询
	 */
	public PageResult selectTeacherByPage(int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Teacher> sysMenus = teacherMapper.selectTeacher();
		PageInfo<Teacher> pageInfo=new PageInfo<Teacher>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	/**
	 * 根据条件查询
	 */
	public PageResult selectTeacherByParams(int pageNum,int pageSize,int collegeId,String name){
		PageHelper.startPage(pageNum, pageSize);

		List<Teacher> sysMenus = teacherMapper.selectTeacherByParams(collegeId,name);
		PageInfo<Teacher> pageInfo=new PageInfo<Teacher>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	/**
	 * 首页展示
	 */
	public List<Teacher> selectTeaccherLimit(int num){
		List<Teacher> teachers=teacherMapper.selectTeacherLimit(num);
		return teachers;
	}

	/**
	 * 根据id查询
	 */
	public Teacher selectTeacherById(Integer id){
		return teacherMapper.selectTeacherById(id);
	}
	/**
	 * 根据teacherNum查询
	 */
	public Teacher selectTeacherByTeacherNum(String teacherNum){
		return teacherMapper.selectTeacherByTeacherNum(teacherNum);
	}

	/**
	 * 根据id删除
	 */
	public int deleteTeacherByIds(List<Integer> ids){
		return teacherMapper.deleteTeacherByIds(ids);
	}

	/**
	 * 添加教师
	 */
	public int insertTeacher(Map map){
		Map resmap=new HashMap();
		resmap.put("name",map.get("name"));
		resmap.put("teacherNum",map.get("teacherNum"));
		resmap.put("collegeId",map.get("collegeId"));
		resmap.put("gender",map.get("gender"));
		resmap.put("tel",map.get("tel"));
		resmap.put("username",map.get("username"));
		resmap.put("password",map.get("password"));
		return teacherMapper.insertTeacher(resmap);
	}

	/**
	 * 更新教师信息
	 */
	public int updateTeacher(Map map) {
		Map resmap=new HashMap();
		resmap.put("id",map.get("id"));
		resmap.put("name",map.get("name"));
		resmap.put("teacherNum",map.get("teacherNum"));
		resmap.put("collegeId",map.get("collegeId"));
		resmap.put("gender",map.get("gender"));
		resmap.put("tel",map.get("tel"));
		resmap.put("username",map.get("username"));
		resmap.put("password",map.get("password"));
		return teacherMapper.updateTeacher(resmap);
	}

	public int updateTeacherByModel(Teacher teacher) {
		Map resmap=new HashMap();
		resmap.put("id",teacher.getId());
		resmap.put("name",teacher.getName());
		resmap.put("teacherNum",teacher.getTeacherNum());
		resmap.put("tel",teacher.getTel());
		resmap.put("username",teacher.getUsername());
		resmap.put("password",teacher.getPassword());
		return teacherMapper.updateTeacherByModel(resmap);
	}

	/**
	 * 判断是否关注教师
	 */
	public boolean isFollow(int teacherId,int studentId){
		if(teacherMapper.selectTeacherFollow(teacherId,studentId)!=null){
			return true;
		}
		else return false;
	}


	/**
	 *
	 * 关注老师
	 */
	public Integer insertTeacherFollow(int teacherId,int studentId){
		int i=teacherMapper.insertTeacherFollow(teacherId,studentId);
		return i;
	}

	/**
	 * 取消关注
	 */
	public Integer deleteTeacherFollow(int teacherId,int studentId){
		int i=teacherMapper.deleteTeacherFollow(teacherId,studentId);
		return i;
	}

	/**
	 *
	 * 查询关注的老师
	 */
    public PageResult selectTeacherFollowByPage(Integer pageNum, Integer pageSize, Integer studentId) {
		PageHelper.startPage(pageNum, pageSize);
		List<Integer> ids=teacherMapper.selectTeacherFollowId(studentId);
		List<Teacher> sysMenus=new LinkedList<>();
		for(int i=0;i<ids.size();i++){
			sysMenus.add(teacherMapper.selectTeacherById(ids.get(i)));
		}
		PageInfo<Teacher> pageInfo=new PageInfo<>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
    }
}
