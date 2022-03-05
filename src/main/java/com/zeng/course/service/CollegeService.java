package com.zeng.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.CollegeMapper;
import com.zeng.course.model.College;
import com.zeng.course.util.PageRequest;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollegeService {
	@Resource
	private CollegeMapper collegeMapper;
	@Autowired
	PageRequest pageRequest;

	
	/**
	 * 分页查询
	 */
	public PageResult selectCollegeByPage(int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<College> sysMenus = collegeMapper.selectCollege();
		PageInfo<College> pageInfo=new PageInfo<College>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}
	
	/**
	 * 根据Id查询学院
	 */
	public College selectCollegeById(int Id){
		return collegeMapper.selectCollegeById(Id);
	}

	/**
	 * 根据id删除
	 */
	public int deleteCollegeByIds(List<Integer> ids){
		return collegeMapper.deleteCollegeByIds(ids);
	}

	/**
	 * 添加学院
	 */
	public int insertCollege(Map map){
		Map resmap=new HashMap();
		resmap.put("name",map.get("name"));
		resmap.put("intro",map.get("intro"));
		return collegeMapper.insertCollege(resmap);
	}

	public int updateCollege(Map map) {
		Map resmap=new HashMap();
		resmap.put("id",map.get("id"));
		resmap.put("name",map.get("name"));
		resmap.put("intro",map.get("intro"));
		return collegeMapper.updateCollege(resmap);
	}

}
