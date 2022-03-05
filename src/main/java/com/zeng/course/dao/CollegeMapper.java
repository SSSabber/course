package com.zeng.course.dao;


import com.zeng.course.model.College;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CollegeMapper {

    List<College> selectCollege();
    int deleteCollegeByIds(List list);
    int insertCollege(Map map);
    int updateCollege(Map map);
    College selectCollegeById(int id);
}