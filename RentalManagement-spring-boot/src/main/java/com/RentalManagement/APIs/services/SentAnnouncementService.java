package com.RentalManagement.APIs.services;

import com.RentalManagement.APIs.allRepositories.SentAnnouncementRepository;
import com.RentalManagement.APIs.entities.SentAnnouncement;
import com.RentalManagement.APIs.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentAnnouncementService {

    private SentAnnouncementRepository sentAnnouncementRepository;

    public SentAnnouncementService(SentAnnouncementRepository sentAnnouncementRepository){
        this.sentAnnouncementRepository = sentAnnouncementRepository;
    }

    public List<SentAnnouncement> getAllActiveSentAnnouncements(){
        return this.sentAnnouncementRepository.findByIsActive(true);
    }


    public List<SentAnnouncement> getAllActiveSentAnnouncementsTenants(){
        return this.sentAnnouncementRepository.findByTenantIsActive(true);
    }

    public List<SentAnnouncement> getAllActiveSentAnnouncementsLandlords(){
        return this.sentAnnouncementRepository.findByLandlordIsActive(true);
    }



    public List<SentAnnouncement> getAllSentAnnouncements(){
        return this.sentAnnouncementRepository.findAll();
    }


    public SentAnnouncement getSentAnnouncementsById(Integer id){
        if (id == null) return null;
        else {
            return this.sentAnnouncementRepository.findById(id).orElse(null);
        }
    }


    public SentAnnouncement insert(SentAnnouncement announcement){
        return this.sentAnnouncementRepository.save(announcement);
    }


    public SentAnnouncement updateSentAnnouncement(SentAnnouncement announcement){
        if (this.sentAnnouncementRepository.findById(announcement.getId()).orElse(null)!=null){
            return this.sentAnnouncementRepository.save(announcement);
        } else return null;

    }



}
