package com.RentalManagement.APIs.contollers;


import com.RentalManagement.APIs.allRepositories.TenantRepository;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(value = {"http://localhost:4200", "http://960176-rental-management.s3-website-us-west-2.amazonaws.com"})
@RestController
@RequestMapping("tenants")
public class TenantController {

    private TenantService tenantService;
    private LandLordService landLordService;

    public TenantController(TenantService tenantService, LandLordService landLordService){
        this.tenantService = tenantService;
        this.landLordService = landLordService;
    }



    @GetMapping("all")
    public List<Tenant> getAllTenants(){
        return this.tenantService.getAllTenants();
    }


    @GetMapping("allActive")
    public List<Tenant> getAllActiveTenants(){
        return this.tenantService.getAllActiveTenants();
    }



    @GetMapping("allActivebyLandlord")
    public ResponseEntity<String> getAllActiveTenants(@RequestParam Integer id){
        LandLord foundLandlord = this.landLordService.getLandLordById(id);
        HttpStatus status;
        if(foundLandlord == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Landlord not found",status);
        } else{
            List<Tenant> allActiveTenants =  foundLandlord.getTenants().stream().filter(tenant -> tenant.isActive()==true).collect(Collectors.toList());
            status = HttpStatus.OK;
            return new ResponseEntity(allActiveTenants, status);
        }

    }


    @GetMapping("{id}")
    public ResponseEntity<LandLord> getTenantById(@PathVariable Integer id){
        Tenant foundTenant = this.tenantService.getTenantById(id);
        HttpStatus status;

        if(foundTenant == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("tenant not found",status);
        }else{
            status = HttpStatus.OK;
            return new ResponseEntity(foundTenant,status);
        }
    }


    @GetMapping("landlordId")
    public ResponseEntity<LandLord> getLandlordIdByTenantId(@RequestParam Integer id){
        Tenant foundTenant = this.tenantService.getTenantById(id);
        HttpStatus status;

        if(foundTenant == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("tenant not found",status);
        }else{
            status = HttpStatus.OK;
            return new ResponseEntity(foundTenant.getLandLord().getId(),status);
        }
    }


//    @GetMapping("login")
//    public ResponseEntity<String> getTenantOrLandLord(@RequestParam String email, @RequestParam String password){
//
//
//    }



    @PostMapping("add")
    public ResponseEntity<String>  postTenant(@RequestBody Tenant tenant, @RequestParam Integer id){
        LandLord foundLandlord = this.landLordService.getLandLordById(id);
        HttpStatus status;
        if(foundLandlord == null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("landlord with this id not found",status);
        }
        else{
            tenant.setLandLord(foundLandlord);
            this.tenantService.insert(tenant);
            status = HttpStatus.OK;
            return new ResponseEntity(tenant,status);
        }

    }



    @PutMapping("update")
    public ResponseEntity<Tenant> putTenant(@RequestBody Tenant tenant){
        Tenant foundtenant = this.tenantService.getTenantById(tenant.getId());
        HttpStatus status;

        if(foundtenant == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("tenant not found",status);
        }
        else{
            status = HttpStatus.OK;
            tenant.setLandLord(foundtenant.getLandLord());
            tenant.setSentAnnouncements(foundtenant.getSentAnnouncements());
            this.tenantService.updateTenant(tenant);
            return new ResponseEntity(tenant,status);
        }
    }



    @DeleteMapping("deactivate")
    public ResponseEntity<String> deactivateTenant(@RequestBody Tenant tenant){
        Tenant foundTenant = this.tenantService.getTenantById(tenant.getId());
        HttpStatus status;

        if(foundTenant == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("tenant not found",status);
        }
        else if(!foundTenant.getFirstName().equals(tenant.getFirstName()))
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Inconsistency between payload and persisted entity",status);
        }
        else{
            //change to found tenant set foundtenant to false and update foundtenant
            status = HttpStatus.OK;
            foundTenant.setActive(false);
            this.tenantService.updateTenant(foundTenant);
            return new ResponseEntity("tenant deactivated",status);
        }
    }


    @PostMapping("addNewLandlord")
    public ResponseEntity<String> addNewLandlordToTenant( @RequestParam Integer landLordId,@RequestParam Integer tenantId){
        Tenant foundTenant = this.tenantService.getTenantById(tenantId);
        LandLord foundLandlord = this.landLordService.getLandLordById(landLordId);
        HttpStatus status;
        if(foundTenant == null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("tenant not found",status);
        } else{
            foundTenant.setLandLord(foundLandlord);
            this.tenantService.updateTenant(foundTenant);
            status = HttpStatus.OK;
            return new ResponseEntity("tenant has new landlord",status);
        }

    }


    // should I even add this ?

//    @GetMapping("allNonLandlordTenants")
//    public ResponseEntity<String> getAllTenantsWithNoLandlord(){
//        List<Tenant> allTenants = this.tenantService.getAllTenants();
////        allTenants.stream().filter(tenant->tenant.getLandLord().isActive()==false).collect(Collectors.toList());
//       List<Tenant> allNonLandlordTenants = new ArrayList<>();
//       for(int i =0;i<allTenants.size();i++){
//           Tenant currentTenant = allTenants.get(i);
//           if (currentTenant.getLandLord().isActive()== false) allNonLandlordTenants.add(currentTenant);
//       }
//        HttpStatus status;
//        status = HttpStatus.OK;
//        return new ResponseEntity(allNonLandlordTenants, status);
//    }


}
