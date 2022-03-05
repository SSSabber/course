package com.zeng.course;

import com.zeng.course.model.CourseFile;
import com.zeng.course.model.SearchModel;
import com.zeng.course.service.FileService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneTest {

    @Autowired
    FileService fileService;

    @Test
    public void testManage() throws IOException {
        //1.采集数据
        List<SearchModel> searchModelList = fileService.searchModelList();
        //2.创建Document文档对象
        List<Document> documents=new ArrayList();
        for(SearchModel searchModel:searchModelList){
            System.out.println(searchModel.toString());
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
            documents.add(document);
        }
        // 3. 创建Analyzer分词器,分析文档，对文档进行分词
        Analyzer analyzer = new IKAnalyzer();
        // 4. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get("D:\\JavaProjects\\course\\dir"));
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

    @Test
    public void testIndexSearch() throws Exception {
        // 1. 创建Query搜索对象
        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 创建搜索解析器，第一个参数：默认Field域，第二个参数：分词器
        QueryParser queryParser = new QueryParser("brandName", analyzer);
        // 创建搜索对象
        Query query = queryParser.parse("intro:概述");
        // 2. 创建Directory流对象,声明索引库位置
        Directory directory = FSDirectory.open(Paths.get("D:\\JavaProjects\\course\\dir"));
        // 3. 创建索引读取对象IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        // 4. 创建索引搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        // 5. 使用索引搜索对象，执行搜索，返回结果集TopDocs
        // 第一个参数：搜索对象，第二个参数：返回的数据条数，指定查询结果最顶部的n条数据返回
        TopDocs topDocs = searcher.search(query, 50);
        System.out.println("查询到的数据总条数是：" + topDocs.totalHits);
        // 获取查询结果集
        ScoreDoc[] docs = topDocs.scoreDocs;
        // 6. 解析结果集
        for (ScoreDoc scoreDoc : docs) {
            // 获取文档
            int docID = scoreDoc.doc;
            Document doc = searcher.doc(docID);
            System.out.println("=============================");
            System.out.println("name="+doc.get("fileName"));
            System.out.println("intro="+doc.get("intro"));
        }
// 7. 释放资源
        reader.close();
    }

    /*
    删除所有索引
     */
    @Test
    public void deleteAllIndex() throws IOException {

        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 创建Directory流对象
        Directory directory = FSDirectory.open(Paths.get("D:\\JavaProjects\\course\\dir"));
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
