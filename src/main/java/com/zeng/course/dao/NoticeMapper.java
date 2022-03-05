package com.zeng.course.dao;


import com.zeng.course.model.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {

    List<Notice> selectNotice();
    List<Notice> selectNoticeLimit(int num);
    int deleteNoticeByIds(List list);
    int insertNotice(Map map);
    int updateNotice(Map map);
    Notice selectNoticeById(int id);
}