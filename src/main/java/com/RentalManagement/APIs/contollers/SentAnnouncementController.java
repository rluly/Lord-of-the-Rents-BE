package com.RentalManagement.APIs.contollers;

import com.RentalManagement.APIs.entities.*;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.SentAnnouncementService;
import com.RentalManagement.APIs.services.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(value = {"http://localhost:4200", "http://960176-rental-management.s3-website-us-west-2.amazonaws.com"})
@RestController
@RequestMapping("sentAnnouncements")
public class SentAnnouncementController {

    private SentAnnouncementService sentAnnouncementService;
    private LandLordService landLordService;
    private TenantService tenantService;



    public SentAnnouncementController(SentAnnouncementService sentAnnouncementService, LandLordService landLordService, TenantService tenantService){
        this.tenantService = tenantService;
        this.sentAnnouncementService = sentAnnouncementService;
        this.landLordService = landLordService;

    }


    @GetMapping("all")
    public List<SentAnnouncement> getAllSentAnnouncements(){
        return this.sentAnnouncementService.getAllSentAnnouncements();
    }


    @GetMapping("allActiveLandlords")
    public ResponseEntity<String> getAllActiveSentAnnouncementsLandlords(@RequestParam Integer id){
        LandLord foundLandlord = this.landLordService.getLandLordById(id);
        HttpStatus status;
        if(foundLandlord == null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("landlord with this id not found",status);
        } else{
            List<SentAnnouncement> allSentAnnouncements = foundLandlord.getSentAnncouncements().stream().filter(announcement -> announcement.getLandlordIsActive()==true).collect(Collectors.toList());
            status = HttpStatus.OK;
            return new ResponseEntity(allSentAnnouncements,status);
        }
    }


    @GetMapping("allActiveTenants")
    public ResponseEntity<String> getAllActiveSentAnnouncementsTenants(@RequestParam Integer id){
        Tenant foundTenant = this.tenantService.getTenantById(id);
        HttpStatus status;
        if(foundTenant == null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("tenant with this id not found",status);
        } else{
            List<SentAnnouncement> allSentAnnouncements = foundTenant.getSentAnnouncements().stream().filter(announcement -> announcement.isTenantIsActive()==true).collect(Collectors.toList());
            status = HttpStatus.OK;
            return new ResponseEntity(allSentAnnouncements,status);
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<SentAnnouncement> getSentAnnouncementById(@PathVariable Integer id){
        SentAnnouncement foundSentAnnouncement = this.sentAnnouncementService.getSentAnnouncementsById(id);
        HttpStatus status;

        if(foundSentAnnouncement == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("announcement not found",status);
        }else{
            status = HttpStatus.OK;
            return new ResponseEntity(foundSentAnnouncement,status);
        }
    }


    @PostMapping("add")
    public ResponseEntity<String>  postSentAnnouncement(@RequestBody SentAnnouncement announcement, @RequestParam Integer landlordId, @RequestParam Integer tenantId){
        LandLord foundLandlord = this.landLordService.getLandLordById(landlordId);
        Tenant foundTenant = this.tenantService.getTenantById(tenantId);
        HttpStatus status;
        if(foundLandlord == null || foundTenant == null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("landlord or tenant with this id not found",status);
        }
        else{
            announcement.setLandLord(foundLandlord);
            announcement.setTenant(foundTenant);
            this.sentAnnouncementService.insert(announcement);
            status = HttpStatus.OK;
            return new ResponseEntity(announcement,status);
        }
    }




    @DeleteMapping("deactivateTenant")
    public ResponseEntity<String> deactivateSentAnnouncementTenant(@RequestBody SentAnnouncement announcement){
        SentAnnouncement foundSentAnnouncement = this.sentAnnouncementService.getSentAnnouncementsById(announcement.getId());
        HttpStatus status;

        if(foundSentAnnouncement == null )
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("sent announcement not found",status);
        }
        else if(!foundSentAnnouncement.getMessage().equals(announcement.getMessage()))
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Inconsistency between payload and persisted entity",status);
        }
        else{
            status = HttpStatus.OK;
            foundSentAnnouncement.setTenantIsActive(false);
            this.sentAnnouncementService.updateSentAnnouncement(foundSentAnnouncement);
            return new ResponseEntity(status);
        }
    }


    @DeleteMapping("deactivateLandlord")
    public ResponseEntity<String> deactivateSentAnnouncementLandlord(@RequestBody SentAnnouncement announcement){
        SentAnnouncement foundSentAnnouncement = this.sentAnnouncementService.getSentAnnouncementsById(announcement.getId());
        HttpStatus status;

        if(foundSentAnnouncement == null )
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("sent announcement not found",status);
        }
        else if(!foundSentAnnouncement.getMessage().equals(announcement.getMessage()))
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Inconsistency between payload and persisted entity",status);
        }
        else{
            status = HttpStatus.OK;
            foundSentAnnouncement.setLandlordIsActive(false);
            this.sentAnnouncementService.updateSentAnnouncement(foundSentAnnouncement);
            return new ResponseEntity(status);
        }
    }




}
