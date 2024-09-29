package com.tech.journalApp.controller;

import com.tech.journalApp.entity.User;
import com.tech.journalApp.entity.journalEntry;
import com.tech.journalApp.repository.userRepository;
import com.tech.journalApp.service.UserService;
import com.tech.journalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            if (userService.findByUserName(user.getUserName()) != null) {
                // Return BAD_REQUEST if the username is already taken
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/userName/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String userName ){
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{userName}/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName , @PathVariable ObjectId id){
        User user = userService.findByUserName(userName);
        if(user != null && !user.equals("")){
            userService.deleteEntry(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
