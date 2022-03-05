package com.zeng.course.service;

import com.zeng.course.dao.SectionMapper;
import com.zeng.course.model.Section;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SectionService {
    @Resource
    SectionMapper sectionMapper;

    public List<Section> selectSectionByCourseId(Integer id){
        List<Section> sections=sectionMapper.selectSectionByCourseId(id);
        return sections;
    }

    public void insertSection(Map<String, Object> map) {
        String sectionName=(String)map.get("sectionName");
        Integer courseId=(Integer)map.get("courseId");
        Integer sort=(Integer)map.get("sort");
        sectionMapper.insertSection(sectionName,courseId,sort);
    }

    public void editSection(Integer sectionId, String sectionName) {
        sectionMapper.updateSection(sectionId,sectionName);
    }

    public void deleteSection(Integer sectionId) {
        sectionMapper.deleteSection(sectionId);
    }
}
