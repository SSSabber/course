package com.zeng.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.NoticeMapper;
import com.zeng.course.model.Notice;
import com.zeng.course.util.PageRequest;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {
	@Resource
	private NoticeMapper noticeMapper;
	@Autowired
	PageRequest pageRequest;

	/**
	 * 分页查询
	 */
	public PageResult selectNoticeByPage(int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Notice> sysMenus = noticeMapper.selectNotice();
		PageInfo<Notice> pageInfo=new PageInfo<Notice>(sysMenus);
		return PageUtils.getPageResult(pageInfo);
	}


	/**
	 * 根据noticeId查询
	 */
	public Notice selectNoticeById(int id){
		return noticeMapper.selectNoticeById(id);
	}

	/**
	 *首页展示
	 */
	public List<Notice> selectNoticeLimit(int num){
		List<Notice> notices=noticeMapper.selectNoticeLimit(num);
		return notices;
	}

	/**
	 * 根据id删除
	 */
	public int deleteNoticeByIds(List<Integer> ids){
		return noticeMapper.deleteNoticeByIds(ids);
	}

	/**
	 * 添加公告
	 */
	public int insertNotice(Map map){
		Map resmap=new HashMap();
		resmap.put("title",map.get("title"));
		resmap.put("content",map.get("content"));
		resmap.put("record_time",new Date());
		return noticeMapper.insertNotice(resmap);
	}

	public int updateNotice(Map map) {
		Map resmap=new HashMap();
		resmap.put("id",map.get("id"));
		resmap.put("title",map.get("title"));
		resmap.put("content",map.get("content"));
		resmap.put("record_time",new Date());
		return noticeMapper.updateNotice(resmap);
	}

}
