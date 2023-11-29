package com.login.rep;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.login.models.Student;

public interface StudentRepository extends MongoRepository<Student, Integer> {

	
	
}
