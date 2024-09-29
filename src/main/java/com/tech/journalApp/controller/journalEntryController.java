package com.tech.journalApp.controller;

import com.tech.journalApp.entity.User;
import com.tech.journalApp.entity.journalEntry;
import com.tech.journalApp.service.UserService;
import com.tech.journalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class journalEntryController {
    @Autowired
    private journalEntryService jouranlEntryService;

    @Autowired
    private UserService userService;

    //becuse of we are not using any database , we created Map to store our data as mention in journal entry class(pojo plane old java object)


    //get mapping is use to just see or review the entries
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<journalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ///post mapping use to insert or create journal entries
    @PostMapping("{userName}")
    public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry MyEntry , @PathVariable String userName){ //@RequestBody use to tell that whatever i am writing ust take it and sore in MyEntry
        //MyEntry is nothing but just ana instenc of journalEntry
        try {
            jouranlEntryService.saveEntry(MyEntry , userName);
            return new ResponseEntity<>(MyEntry , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    @GetMapping("/id/{MyId}")
    public ResponseEntity<journalEntry> getJournalEntryByID(@PathVariable ObjectId MyId){ //pathVariable is something which we give as url/something {"journal/id/2"}
         Optional<journalEntry> journalEntry =  jouranlEntryService.findById(MyId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    //delete mapping use to delete Entry from the data
    @DeleteMapping("/id/{userName}/{MyId}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId MyId , @PathVariable String userName){
        jouranlEntryService.deleteEntry(MyId , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    //put mapping use to update or modify the entry
    @PutMapping("id/{userName}/{id}")
    //asking for id which you want to update and the body of entry
    public ResponseEntity<?> putJournalEntryByID(@PathVariable ObjectId id , @RequestBody journalEntry newEntry , @PathVariable String userName ){
        journalEntry old =  jouranlEntryService.findById(id).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            jouranlEntryService.saveEntry(old);
            return new ResponseEntity<>(old , HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
    //all the functions like put() , get() , remove() is part of HashMap which is part map/set dataStructure
}
