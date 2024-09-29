package com.tech.journalApp.service;

import com.tech.journalApp.entity.User;
import com.tech.journalApp.entity.journalEntry;
import com.tech.journalApp.repository.journalEntryRepository;
import com.tech.journalApp.repository.userRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired //----dependency injection
    private userRepository userRepository;

    public void saveEntry(User user){
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return;
        }
    }

    public void deleteEntry(ObjectId id){
        userRepository.deleteById(id);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
