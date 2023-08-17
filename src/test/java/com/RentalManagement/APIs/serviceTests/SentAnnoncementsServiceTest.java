package com.RentalManagement.APIs.serviceTests;

import com.RentalManagement.APIs.allRepositories.LandLordRespoistory;
import com.RentalManagement.APIs.allRepositories.SentAnnouncementRepository;
import com.RentalManagement.APIs.entities.SentAnnouncement;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.SentAnnouncementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class SentAnnoncementsServiceTest {

    @Mock
    private SentAnnouncementRepository sentAnnouncementRepository;

    @InjectMocks
    private SentAnnouncementService sentAnnouncementService;

    @Test
    public void getAllActiveSentAnnouncementsTest() {
        sentAnnouncementService.getAllActiveSentAnnouncements();
        verify(sentAnnouncementRepository, times(1)).findByIsActive(true);
    }

    @Test
    public void getAllLandLordsTest(){
        sentAnnouncementService.getAllSentAnnouncements();
        verify(sentAnnouncementRepository, times(1)).findAll();
    }

    @Test
    public void getAllActiveSentAnnouncementsTenantsTest(){
        sentAnnouncementService.getAllActiveSentAnnouncementsTenants();
        verify(sentAnnouncementRepository, times(1)).findByTenantIsActive(true);
    }

    @Test
    public void getAllActiveSentAnnouncementsLandlordsTest(){
        sentAnnouncementService.getAllActiveSentAnnouncementsLandlords();
        verify(sentAnnouncementRepository, times(1)).findByLandlordIsActive(true);
    }

    @Test
    public void getSentAnnouncementByIdTest(){
        int paramId = 1;
        sentAnnouncementService.getSentAnnouncementsById(paramId);
        verify(sentAnnouncementRepository, times(1)).findById(paramId);
    }

    @Test
    public void insertSentAnnouncementTest(){
        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("hello");
        sentAnnouncementService.insert(msg);
        verify(sentAnnouncementRepository, times(1)).save(msg);
    }

    @Test
    public void updateTenantTest(){
        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("hello");

        sentAnnouncementService.insert(msg);

        int paramId = 1;
        sentAnnouncementService.getSentAnnouncementsById(paramId);
        verify(sentAnnouncementRepository, times(1)).findById(paramId);

        sentAnnouncementService.updateSentAnnouncement(msg);
        verify(sentAnnouncementRepository, times(1)).save(msg);
    }



}
