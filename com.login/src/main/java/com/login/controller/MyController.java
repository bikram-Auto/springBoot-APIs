package com.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.models.Student;
import com.login.rep.StudentRepository;

@RestController
@RequestMapping("/student")
public class MyController {

	@Autowired
	private StudentRepository studentrepository;
	
	@PostMapping("/")
	public ResponseEntity<?> addStudent(@RequestBody Student student){		
		Student save = this.studentrepository.save(student);
		return ResponseEntity.ok(save);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getStudent(){				
		return ResponseEntity.ok(this.studentrepository.findAll());
	}
}
