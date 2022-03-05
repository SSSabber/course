package com.zeng.course.service;

import com.zeng.course.model.SearchModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {
    @Autowired
    FileService fileService;
    @Value("${index.url}")
    private String indexUrl;

    /* 创建索引
     */
    public void createIndex() throws IOException {
        //1.采集数据
        List<SearchModel> searchModelList = fileService.searchModelList();
        //2.创建Document文档对象
        List<Document> documents=new ArrayList();
        for(SearchModel searchModel:searchModelList){
            Document document=new Document();
            //课程id，存储，不索引，不分词
            document.add(new StoredField("courseId", searchModel.getCourse().getId().toString()));
            //课程名称，存储，索引，分词
            document.add(new TextField("courseName",searchModel.getCourse().getName(), Field.Store.YES));
            //学院名称，存储，索引，分词
            document.add(new TextField("college", searchModel.getCourse().getCollege(), Field.Store.YES));
            //教师姓名，存储，索引，分词
            document.add(new TextField("teacher", searchModel.getCourse().getTeacher(), Field.Store.YES));
            //课程介绍，存储，索引，分词
            document.add(new TextField("courseIntro", searchModel.getCourse().getIntro(), Field.Store.YES));
            //章节名称，存储，索引，分词
            document.add(new TextField("sectionName", searchModel.getSection().getName(), Field.Store.YES));
            //文件id，存储，不索引，不分词
            document.add(new StoredField("fileId",searchModel.getCourseFile().getId().toString()));
            //文件名称，存储，索引，分词
            document.add(new TextField("fileName", searchModel.getCourseFile().getName(), Field.Store.YES));
            //文件介绍，存储，索引，分词
            document.add(new TextField("intro", searchModel.getCourseFile().getIntro(), Field.Store.YES));
            //文件路径，存储，不索引，不分词
            document.add(new StoredField("path", searchModel.getCourseFile().getPath()));
            documents.add(document);
        }
        // 3. 创建Analyzer分词器,分析文档，对文档进行分词
        Analyzer analyzer = new IKAnalyzer();
        // 4. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get(indexUrl));
        // 5. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 6.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 7.写入到索引库，通过IndexWriter添加文档对象document
        for (Document doc : documents) {
            indexWriter.addDocument(doc);
        }
        // 8.释放资源
        indexWriter.close();
    }

    /*添加索引
     */
    public void insertIndex(SearchModel searchModel) throws IOException{

        Document document=new Document();
        document.add(new StoredField("courseId", searchModel.getCourse().getId().toString()));
        document.add(new TextField("courseName",searchModel.getCourse().getName(),
                Field.Store.YES));
        document.add(new TextField("college", searchModel.getCourse().getCollege(),
                Field.Store.YES));
        document.add(new TextField("teacher", searchModel.getCourse().getTeacher(),
                Field.Store.YES));
        document.add(new TextField("courseIntro", searchModel.getCourse().getIntro(),
                Field.Store.YES));
        document.add(new TextField("sectionName", searchModel.getSection().getName(),
                Field.Store.YES));
        document.add(new StoredField("fileId",searchModel.getCourseFile().getId().toString()));
        document.add(new TextField("fileName", searchModel.getCourseFile().getName(),
                Field.Store.YES));
        document.add(new TextField("intro", searchModel.getCourseFile().getIntro(),
                Field.Store.YES));
        document.add(new StoredField("path", searchModel.getCourseFile().getPath()));

        // 3. 创建Analyzer分词器,分析文档，对文档进行分词
        Analyzer analyzer = new IKAnalyzer();
        // 4. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get(indexUrl));
        // 5. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 6.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 7.写入到索引库，通过IndexWriter添加文档对象document
        indexWriter.addDocument(document);

        // 8.释放资源
        indexWriter.close();
    }

    /*更新索引
     */
    public void updateIndex(SearchModel searchModel) throws IOException {
        Document document=new Document();
        document.add(new StoredField("courseId", searchModel.getCourse().getId().toString()));
        document.add(new TextField("courseName",searchModel.getCourse().getName(),
                Field.Store.YES));
        document.add(new TextField("college", searchModel.getCourse().getCollege(),
                Field.Store.YES));
        document.add(new TextField("teacher", searchModel.getCourse().getTeacher(),
                Field.Store.YES));
        document.add(new TextField("courseIntro", searchModel.getCourse().getIntro(),
                Field.Store.YES));
        document.add(new TextField("sectionName", searchModel.getSection().getName(),
                Field.Store.YES));
        document.add(new StoredField("fileId",searchModel.getCourseFile().getId().toString()));
        document.add(new TextField("fileName", searchModel.getCourseFile().getName(),
                Field.Store.YES));
        document.add(new TextField("intro", searchModel.getCourseFile().getIntro(),
                Field.Store.YES));
        document.add(new StoredField("path", searchModel.getCourseFile().getPath()));

        // 3. 创建Analyzer分词器,分析文档，对文档进行分词
        Analyzer analyzer = new IKAnalyzer();
        // 4. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get(indexUrl));
        // 5. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 6.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        indexWriter.updateDocument(new Term("fileId",searchModel.getCourseFile().getId().toString()),document);

    }

    /*删除选定索引
     */
    public void deleteIndex(List<SearchModel> searchModelList) throws IOException {
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 创建Directory流对象
        Directory directory = FSDirectory.open(Paths.get("D:\\JavaProjects\\course\\dir"));
        // 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 删除选定索引
        for(SearchModel searchModel:searchModelList){
            indexWriter.deleteDocuments(new Term("fileId",searchModel.getCourseFile().getId().toString()));
        }
        // 释放资源
        indexWriter.close();
    }

    /*删除所有索引
     */
    public void deleteAllIndex() throws IOException {
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 创建Directory流对象
        Directory directory = FSDirectory.open(Paths.get(indexUrl));
        // 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 全部删除
        indexWriter.deleteAll();
        // 释放资源
        indexWriter.close();
    }
}
