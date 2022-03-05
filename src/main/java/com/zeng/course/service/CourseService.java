package com.zeng.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.CourseMapper;
import com.zeng.course.model.Course;
import com.zeng.course.util.PageRequest;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CourseService {
	@Resource
	private CourseMapper courseMapper;
	@Autowired
	PageRequest pageRequest;


	/**
	 * 分页查询
	 */
	public PageResult selectCourseByPage(int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Course> sysMenus = courseMapper.selectCourse();
		PageInfo<Course> pageInfo=new PageInfo<Course>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	/**
	 * 根据条件查询
	 */
	public PageResult selectCourseByParams(int pageNum,int pageSize,int collegeId){
		PageHelper.startPage(pageNum, pageSize);
		List<Course> sysMenus = courseMapper.selectCourseByParams(collegeId);
		PageInfo<Course> pageInfo=new PageInfo<Course>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

	/**
	 * 根据教师id查询课程
	 */
	public PageResult selectCourseByTeacherId(int pageNum,int pageSize,int teacherId){
		PageHelper.startPage(pageNum, pageSize);
		List<Integer> ids=courseMapper.selectCourseIdByTeacherId(teacherId);
		List<Course> sysMenus=new LinkedList<>();
		for(int i=0;i<ids.size();i++){
			Course course=courseMapper.selectCourseById(ids.get(i));
			if(course!=null){
				sysMenus.add(course);
			}
		}
		PageInfo<Course> pageInfo=new PageInfo<Course>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}


	/**
	首页进行展示
	 */
	public List<Course> selectCourseLimit(int num){
		List<Course> courses=courseMapper.selectCourseLimit(num);
		return  courses;
	}

	/**
	 * 根据id查询课程
	 */
	public Course selectCourseById(Integer id){
		return courseMapper.selectCourseById(id);
	}

	/**
	 * 根据id查询教师id
	 */
	public Integer selectTeacherIdByCourseId(Integer id){
		return courseMapper.selectTeacherIdByCourseId(id);
	}

	/**
	 * 根据id删除
	 */
	public int deleteCourseByIds(List ids){
		return courseMapper.deleteCourseByIds(ids);
	}

	/**
	 * 添加课程
	 */
	public int insertCourse(Map map){
		Map resmap=new HashMap();
		resmap.put("name",map.get("name"));
		resmap.put("teacherId",map.get("teacherId"));
		resmap.put("intro",map.get("intro"));
		return courseMapper.insertCourse(resmap);
	}

	public int updateCourse(Map map) {
		Map resmap=new HashMap();
		resmap.put("id",map.get("id"));
		resmap.put("name",map.get("name"));
		resmap.put("teacherId",map.get("teacherId"));
		resmap.put("intro",map.get("intro"));
		return courseMapper.updateCourse(resmap);
	}

	public boolean isFollow(int courseId,int studentId){
		if(courseMapper.isCourseFollow(courseId,studentId)!=null){
			return true;
		}
		else return false;
	}


	public Integer insertCourseFollow(int courseId,int studentId){
		int i=courseMapper.insertCourseFollow(courseId,studentId);
		return i;
	}

	public Integer deleteCourseFollow(int courseId,int studentId){
		int i=courseMapper.deleteCourseFollow(courseId,studentId);
		return i;
	}


	/**
	 *查询收藏的课程
	 */
	public PageResult selectCourseFollowByPage(int pageNum, int pageSize,int studentId) {
		PageHelper.startPage(pageNum, pageSize);
		List<Integer> ids=courseMapper.selectCourseFollowId(studentId);
		List<Course> sysMenus=new LinkedList<>();
		for(int i=0;i<ids.size();i++){
			sysMenus.add(courseMapper.selectCourseById(ids.get(i)));
		}
		PageInfo<Course> pageInfo=new PageInfo<Course>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}

}
