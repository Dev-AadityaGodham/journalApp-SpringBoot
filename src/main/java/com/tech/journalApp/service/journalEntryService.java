package com.tech.journalApp.service;

import com.tech.journalApp.entity.User;
import com.tech.journalApp.entity.journalEntry;
import com.tech.journalApp.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {
    @Autowired //----dependency injection
    private journalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(journalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            journalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving entry");
        }
    }
    public void saveEntry(journalEntry journalEntry){
        try {
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            return;
        }
    }

    public void deleteEntry(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
    public List<journalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<journalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

}
