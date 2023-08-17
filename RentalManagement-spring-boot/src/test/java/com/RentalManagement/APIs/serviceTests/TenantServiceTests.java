package com.RentalManagement.APIs.serviceTests;

import com.RentalManagement.APIs.allRepositories.LandLordRespoistory;
import com.RentalManagement.APIs.allRepositories.TenantRepository;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TenantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class TenantServiceTests {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    private TenantService tenantService;


    @Test
    public void getAllActiveTenantsTest() {
        tenantService.getAllActiveTenants();
        verify(tenantRepository, times(1)).findByIsActive(true);
    }


    @Test
    public void getAllTenantsTest(){
        tenantService.getAllTenants();
        verify(tenantRepository, times(1)).findAll();
    }

    @Test
    public void getTenantByIdTest(){
        int paramId = 1;
        tenantService.getTenantById(paramId);
        verify(tenantRepository, times(1)).findById(paramId);
    }

    @Test
    public void insertTenantTest(){
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");

        tenantService.insert(tenant);
        verify(tenantRepository, times(1)).save(tenant);
    }


    @Test
    public void updateTenantTest(){
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");

        tenantService.insert(tenant);

        int paramId = 1;
        tenantService.getTenantById(paramId);
        verify(tenantRepository, times(1)).findById(paramId);

        tenantService.updateTenant(tenant);
        verify(tenantRepository, times(1)).save(tenant);
    }

    @Test
    public void getTenantByEmailTest(){
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");

        tenantService.insert(tenant);

        tenantService.getLandTenantByEmail(tenant.getEmail());

        verify(tenantRepository, times(1)).findByEmail(tenant.getEmail());

    }


    @Test
    public void getTenantByEmailAndPasswordTest(){
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");

        tenantService.insert(tenant);

        tenantService.getTenantByEmailAndPassword(tenant.getEmail(),tenant.getPassword());
        verify(tenantRepository, times(1)).findByEmailAndPassword(tenant.getEmail(),tenant.getPassword());
    }


    @Test
    public void getAllTenantsByLandLordIdTest(){
        LandLord l1 = new LandLord();
        l1.setId(1);
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");
        tenant.setLandLord(l1);

        tenantService.insert(tenant);

        tenantService.getAllTenantsByLandLordId(l1.getId());
        verify(tenantRepository, times(1)).findAllById(Collections.singleton(l1.getId()));

    }






}
