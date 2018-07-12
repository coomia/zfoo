package com.zfoo.ztest.mongodb;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Ignore;
import org.junit.Test;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-11 12:20
 */
@Ignore
public class MongoTest {

    @Test
    public void find() {
        // To connect to mongodb server
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // If MongoDB in secure mode, authentication is required.
        // Now connect to your databases
        MongoDatabase mongodb = mongoClient.getDatabase("school");

        System.out.println("Connect to database successfully!");
        System.out.println("MongoDatabase inof is : " + mongodb.getName());

        System.out.println("当前数据库中的所有集合是：");
        for (String name : mongodb.listCollectionNames()) {
            System.out.println(name);
        }

        // 查找并且便利集合student的所有文档
        MongoCollection<Document> collection = mongodb.getCollection("student");
        System.out.println("Collection created successfully");
        for (Document doc : collection.find()) {
            System.out.println(doc);
        }

    }


    @Test
    public void insert() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongodb = mongoClient.getDatabase("school");

        // 查找并且便利集合student的所有文档
        MongoCollection<Document> collection = mongodb.getCollection("student");

        // 插入一个文档
        Document document = new Document("_id", 1).append("name", "hello mongodb");
        collection.insertOne(document);

        collection.find().forEach((Block<Document>) doc -> {
            System.out.println(doc.toJson());
        });
    }

    @Test
    public void update() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongodb = mongoClient.getDatabase("school");

        // 查找并且便利集合student的所有文档
        MongoCollection<Document> collection = mongodb.getCollection("student");

        // 更新一个文档
        collection.updateOne(eq("_id", 1), new Document("$set", new Document("name", "new hello mongodb")));

        collection.find().forEach((Block<Document>) doc -> {
            System.out.println(doc.toJson());
        });
    }

    @Test
    public void delete() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongodb = mongoClient.getDatabase("school");

        MongoCollection<Document> collection = mongodb.getCollection("student");

        // 删除一个文档
        collection.deleteOne(eq("_id", 1));

        collection.find().forEach((Block<Document>) doc -> {
            System.out.println(doc.toJson());
        });
    }

}
