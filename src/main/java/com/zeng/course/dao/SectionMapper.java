package com.zeng.course.dao;


import com.zeng.course.model.Section;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SectionMapper {
    List<Section> selectSectionByCourseId(Integer id);

    void insertSection(@Param("sectionName") String sectionName, @Param("courseId") Integer courseId, @Param("sort") Integer sort);

    void updateSection(@Param("sectionId") Integer sectionId,@Param("sectionName") String sectionName);

    void deleteSection(Integer sectionId);

    Section selectSectionById(Integer id);
}
