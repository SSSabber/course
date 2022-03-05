package com.zeng.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeng.course.dao.CourseMapper;
import com.zeng.course.dao.FileMapper;
import com.zeng.course.dao.SectionMapper;
import com.zeng.course.model.*;
import com.zeng.course.util.PageResult;
import com.zeng.course.util.PageUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    @Resource
    FileMapper fileMapper;
    @Resource
    CourseMapper courseMapper;
    @Resource
    SectionMapper sectionMapper;
    @Autowired
    IndexService indexService;

    public CourseFile selectFileById(int id){
        CourseFile courseFile =fileMapper.selectFileById(id);
        return courseFile;
    }

    public int insertFileDownload(int studentId,int fileId){
        return fileMapper.insertFileDownload(studentId,fileId);
    }

    public PageResult selectFileDownloads(int pageNum,int pageSize,int studentId){
        PageHelper.startPage(pageNum, pageSize);
        List<File_download> sysMenus = fileMapper.selectFileDownloadByStudentId(studentId);
        PageInfo<File_download> pageInfo=new PageInfo<>(sysMenus);
        return PageUtils.getPageResult(pageInfo);
    }

    public int deleteFileDownloads(List<Integer> ids){
        return fileMapper.deleteFileDownloads(ids);
    }

    public int deleteFileByIds(List<Integer> ids) {
       return fileMapper.deleteFileByIds(ids);
    }

    public void insertFile(Integer sectionId, String name, String intro, String path) {
        fileMapper.insertFile(sectionId,name,intro,path);
    }

    public void uploadFile(Integer fId, Integer sectionId, String name, String intro, String path) {
        fileMapper.updateFile(fId,sectionId,name,intro,path);
    }

    /*
    查找所有查询实体类
     */
    public List<SearchModel> searchModelList(){
        List<CourseFile> courseFileList=fileMapper.selectFileList();
        List<SearchModel> searchModelList=new ArrayList<>();
        for(CourseFile courseFile:courseFileList){
            Section section=sectionMapper.selectSectionById(courseFile.getSectionId());
            Course course=courseMapper.selectCourseById(section.getCourseId());
            SearchModel searchModel=new SearchModel();
            searchModel.setCourseFile(courseFile);
            searchModel.setSection(section);
            searchModel.setCourse(course);
            searchModelList.add(searchModel);
        }
        return searchModelList;
    }

//    public SearchModel searchModelByFileId(Integer id){
//        CourseFile courseFile=fileMapper.selectFileById(id);
//        List<SearchModel> searchModelList=new ArrayList<>();
//        Section section=sectionMapper.selectSectionById(courseFile.getSectionId());
//        Course course=courseMapper.selectCourseById(section.getCourseId());
//        SearchModel searchModel=new SearchModel();
//        searchModel.setCourseFile(courseFile);
//        searchModel.setSection(section);
//        searchModel.setCourse(course);
//        searchModelList.add(searchModel);
//        return searchModel;
//    }

    public PageResult searchSource(int pageNum,int pageSize,String queryString) throws ParseException, IOException, InvalidTokenOffsetsException {
        PageHelper.startPage(pageNum, pageSize);
        //创建分词器
        Analyzer analyzer=new IKAnalyzer();
        //根据关键字搜索条件封装
        MultiFieldQueryParser queryParser=new MultiFieldQueryParser(new String[]{"courseName","courseIntro","college",
        "teacher","sectionName","fileName","fileIntro"},analyzer);
        Query query=null;
        if(queryString.equals(" ")){
            //如果关键词为空，查询所有
            //query=queryParser.parse("*:*");
            return new PageResult();
        }else{
            //如果关键词不为空，查询输入语句
            query=queryParser.parse(queryString);
        }

        //创建Directory流对象,声明索引库位置
        Directory directory = FSDirectory.open(Paths.get("D:\\JavaProjects\\course\\dir"));
        //创建索引读取对象IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        //创建索引搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        //查询
        TopDocs topDocs=searcher.search(query,30);

        //获取查询结果集
        ScoreDoc[] docs=topDocs.scoreDocs;
        List<SearchModel> sysMenus = new ArrayList<>();
        // 关键字高亮显示的html标签，需要导入lucene-highlighter-xxx.jar
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

        for(int i=(pageNum-1)*pageSize;i<topDocs.totalHits;i++){
            //读取文档
            Document doc=reader.document(docs[i].doc);
            SearchModel searchModel=new SearchModel();

            Course course=new Course();
            Section section=new Section();
            CourseFile courseFile=new CourseFile();

            course.setId(Integer.parseInt(doc.get("courseId")));
            //内容增加高亮显示
            course.setName(ifnull(highlighter.getBestFragment(analyzer,"courseName",doc.get("courseName")),doc.get("courseName")));
            course.setIntro(ifnull(highlighter.getBestFragment(analyzer,"courseIntro",doc.get("courseIntro")),doc.get("courseIntro")));
            course.setCollege(ifnull(highlighter.getBestFragment(analyzer,"college",doc.get("college")),doc.get("college")));
            course.setTeacher(ifnull(highlighter.getBestFragment(analyzer,"teacher",doc.get("teacher")),doc.get("teacher")));
            section.setName(ifnull(highlighter.getBestFragment(analyzer,"sectionName",doc.get("sectionName")),doc.get("sectionName")));

            courseFile.setId(Integer.parseInt(doc.get("fileId")));
            courseFile.setName(ifnull(highlighter.getBestFragment(analyzer,"fileName",doc.get("fileName")),doc.get("fileName")));
            courseFile.setIntro(ifnull(highlighter.getBestFragment(analyzer,"intro",doc.get("intro")),doc.get("intro")));
            courseFile.setPath(doc.get("path"));

            searchModel.setCourse(course);
            searchModel.setSection(section);
            searchModel.setCourseFile(courseFile);
            sysMenus.add(searchModel);
        }

        PageInfo<SearchModel> pageInfo=new PageInfo<>(sysMenus);
        return PageUtils.getPageResult(pageInfo);
    }

    public String ifnull(String s1,String s2){
        if(s1==null){
            return s2;
        }else{
            return s1;
        }
    }
}
