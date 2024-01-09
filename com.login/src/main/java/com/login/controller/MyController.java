package com.login.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.MongoDBService.MongoDBService;

@RestController
@RequestMapping("/user")
public class MyController {
	
	@Autowired
    private final MongoDBService mongoDBService;
	
	public MyController(MongoDBService mongoDBService) {
        this.mongoDBService = mongoDBService;
    }
	
	@GetMapping("/")
    public List<Map<String, Object>> getAllDocuments() {
        return mongoDBService.getAll("users");
    }


    @PostMapping("/")
    public Map<String, Object> createDocument(@RequestBody Map<String, Object> document) {
        try {
            mongoDBService.create("users", document);
            return Map.of("success", true, "message", "Document inserted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false, "message", "Error during document insertion");
        }
    }
    
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyUserByNameAndDate(@RequestBody Map<String, Object> requestPayload) {
        String name = (String) requestPayload.get("name");
        String date = (String) requestPayload.get("date");

        @SuppressWarnings("rawtypes")
		List<Map> result = mongoDBService.verifyUserByNameAndDate(name, date);

        if (!result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("statusCode", 200, "message", "User verified", "data", result));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("statusCode", 401, "message", "User not verified"));
        }
    }

//	@Autowired
//	private StudentRepository studentrepository;
//	
//	@PostMapping("/")
//	public ResponseEntity<?> addStudent(@RequestBody Student student){		
//		Student save = this.studentrepository.save(student);
//		return ResponseEntity.ok(save);
//	}
//	
//	@GetMapping("/")
//	public ResponseEntity<?> getStudent(){				
//		return ResponseEntity.ok(this.studentrepository.findAll());
//	}
}
