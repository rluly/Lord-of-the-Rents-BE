package com.RentalManagement.APIs.contollers;

import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.SentAnnouncement;
import com.RentalManagement.APIs.entities.Task;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = {"http://localhost:4200", "http://960176-rental-management.s3-website-us-west-2.amazonaws.com"})
@RestController
@RequestMapping("landlords")
public class LandLordController {

    private LandLordService landLordService;
    private TenantService tenantService;

    public LandLordController(LandLordService landLordService, TenantService tenantService){
        this.landLordService = landLordService;
        this.tenantService = tenantService;
    }


    @GetMapping("all")
    public List<LandLord> getLandLord(){
        return this.landLordService.getAllLandLords();
    }

    @GetMapping("allActive")
    public List<LandLord> getAllActiveLandLords(){
        return this.landLordService.getAllActiveLandLords();
    }




    @PostMapping("add")
    public LandLord postLandLord(@RequestBody LandLord landlord){
        return this.landLordService.insertLandLord(landlord);
    }


    @PutMapping("update")
    public ResponseEntity<LandLord> putLandlord(@RequestBody LandLord landlord){
        LandLord foundlandlord = this.landLordService.getLandLordById(landlord.getId());
        HttpStatus status;

        if(foundlandlord == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("landlord not found",status);
        }
        else{
            status = HttpStatus.OK;
            landlord.setTenants(foundlandlord.getTenants());
            landlord.setSentAnncouncements(foundlandlord.getSentAnncouncements());
            landlord.setTasks(foundlandlord.getTasks());
            this.landLordService.updateLandLord(landlord);
            return new ResponseEntity(landlord,status);
        }
    }



    @GetMapping("{id}")
    public ResponseEntity<LandLord> getLandLordById(@PathVariable Integer id){
        LandLord foundLandlord = this.landLordService.getLandLordById(id);
        HttpStatus status;

        if(foundLandlord == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Landlord not found",status);
        }else{
            status = HttpStatus.OK;
            return new ResponseEntity(foundLandlord,status);
        }
    }





    @DeleteMapping("deactivate")
    public ResponseEntity<String> deactivateLandLord(@RequestBody LandLord landlord){
        LandLord foundLandlord = this.landLordService.getLandLordById(landlord.getId());
        HttpStatus status;

        if(foundLandlord == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("landlord not found",status);
        }
        else if(!foundLandlord.getFirstName().equals(landlord.getFirstName()))
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Inconsistency between payload and persisted entity",status);
        }
        else{
            status = HttpStatus.OK;
            foundLandlord.setActive(false);

            // SHould i keep this in or take out
            List<Tenant> allTenants = foundLandlord.getTenants();
            allTenants.forEach(tenant -> tenant.setLandLord(null));

            this.landLordService.updateLandLord(foundLandlord);
            return new ResponseEntity("landlord deactivated",status);
        }
    }




    @GetMapping("loginByEmail")
    public ResponseEntity<String> getLandLordOrTenantByEmail(@RequestParam String email){
        LandLord foundLandlord = this.landLordService.getLandlordByEmail(email);
        Tenant foundTenant = this.tenantService.getLandTenantByEmail(email);
        HttpStatus status;
        if (foundLandlord != null){
            status = HttpStatus.OK;
            return new ResponseEntity(foundLandlord, status);
        }  else if (foundTenant != null){
            status = HttpStatus.OK;
            return new ResponseEntity(foundTenant, status);
        } else{
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("none", status);
        }

    }





}
