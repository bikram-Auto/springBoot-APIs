package com.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.login.config.mongoDBService.MongoDBService;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController")
public class MyController {
    private final MongoDBService mongoDBService;

    @Autowired
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
            return Map.of(
                    "success", true,
                    "message", "Document inserted successfully"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(
                    "success", false,
                    "message", "Error during document insertion"
            );
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
                    .body(Map.of(
                            "body", result,
                            "statusCode", 200,
                            "message", "User verified"
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "statusCode", 401,
                            "message", "User not verified"
                    ));
        }
    }

    @GetMapping("/userByID")
    public ResponseEntity<Map<String, Object>> getUserByID(@RequestParam Integer userid) {
        @SuppressWarnings("rawtypes")
        List<Map> result = mongoDBService.findUserByID(userid);
        Map<String, Object> responseBody = new HashMap<>();
        if (!result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of(
                            "Body", result,
                            "StatusCode", 200,
                            "Message", "Successful"
                    ));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "StatusCode", 401,
                            "Message", "User not Available"
                    ));
        }

    }
}
