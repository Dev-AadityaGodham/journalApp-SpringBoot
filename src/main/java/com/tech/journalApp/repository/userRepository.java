package com.tech.journalApp.repository;

import com.tech.journalApp.entity.User;
import com.tech.journalApp.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}


//controller service repository