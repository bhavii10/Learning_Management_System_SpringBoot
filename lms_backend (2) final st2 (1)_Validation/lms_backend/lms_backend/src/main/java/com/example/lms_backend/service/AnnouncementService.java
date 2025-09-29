package com.example.lms_backend.service;

import com.example.lms_backend.model.Announcement;
import com.example.lms_backend.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository repository;

    public Announcement createAnnouncement(Announcement announcement) {
        return repository.save(announcement);
    }

    public List<Announcement> getAllAnnouncements() {
        return repository.findAll();
    }

    public Optional<Announcement> getAnnouncementById(Integer id) {
        return repository.findById(id);
    }

    public Announcement updateAnnouncement(Integer id, Announcement incoming) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(incoming.getTitle());
            existing.setContent(incoming.getContent());
            existing.setAuthor(incoming.getAuthor());
            existing.setPinned(incoming.getPinned());
            existing.setStartDate(incoming.getStartDate());
            existing.setEndDate(incoming.getEndDate());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Announcement not found"));
    }

    public void deleteAnnouncement(Integer id) {
        repository.deleteById(id);
    }
}