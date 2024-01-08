package com.login.MongoDBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService {

	@Autowired
    private MongoTemplate mongoTemplate;

    public void create(String collectionName, Map<String, Object> document) {
        try {
            MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
            Document dbDocument = new Document(document);
            collection.insertOne(dbDocument);
            System.out.println("Document inserted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during database operation", e);
        }
    }
    
    public List<Map<String, Object>> getAll(String collectionName) {
        try {
            MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
            FindIterable<Document> documents = collection.find();
            MongoCursor<Document> cursor = documents.iterator();
            List<Map<String, Object>> result = new ArrayList<>();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                result.add(document);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during database operation", e);
        }
    }
    
    @SuppressWarnings("rawtypes")
	public List<Map> verifyUserByNameAndDate(String name, String date) {
        try {
            Query query = new Query(Criteria.where("name").is(name).and("date").is(date));
            return mongoTemplate.find(query, Map.class, "users");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during database operation", e);
        }
    }
    
    
    
    
    
    
    
    
}
