package com.example.lms_backend.controller;

import com.example.lms_backend.model.Announcement;
import com.example.lms_backend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    @PostMapping
    public ResponseEntity<Announcement> create(@RequestBody Announcement announcement) {
        return ResponseEntity.ok(service.createAnnouncement(announcement));
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getAll() {
        return ResponseEntity.ok(service.getAllAnnouncements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getAnnouncementById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Announcement> update(@PathVariable Integer id, @RequestBody Announcement announcement) {
        return ResponseEntity.ok(service.updateAnnouncement(id, announcement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.deleteAnnouncement(id);
        return ResponseEntity.ok("Announcement deleted");
    }
}