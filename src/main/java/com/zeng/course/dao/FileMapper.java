package com.zeng.course.dao;

import com.zeng.course.model.CourseFile;
import com.zeng.course.model.File_download;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {

    CourseFile selectFileById(@Param("id")int id);
    int insertFileDownload(@Param("studentId")int studentId,@Param("fileId")int fileId);
    List<File_download> selectFileDownloadByStudentId(@Param("studentId")int studentId);
    int deleteFileDownloads(List<Integer> ids);

    int deleteFileByIds(List<Integer> ids);

    void insertFile(@Param("sectionId") Integer sectionId, @Param("name") String name, @Param("intro") String intro, @Param("path") String path);

    void updateFile(@Param("fId") Integer fId, @Param("sectionId") Integer sectionId, @Param("name") String name, @Param("intro") String intro, @Param("path") String path);

    List<CourseFile> selectFileList();
}
