package com.RentalManagement.APIs.controllerTests;

import com.RentalManagement.APIs.contollers.LandLordController;
import com.RentalManagement.APIs.contollers.TenantController;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TenantService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@WebMvcTest(TenantController.class)
public class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    //
    @MockBean
    private LandLordService landLordService;


    @MockBean
    private TenantService tenantService;


    @Test
    public void getAllTenantsTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tenants/all")
        );
        verify(tenantService, times(1)).getAllTenants();
    }


    @Test
    public void getAllActiveTenantsTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tenants/allActive")
        );
        verify(tenantService, times(1)).getAllActiveTenants();
    }



    @Test
    public void updateTenantTest() throws Exception{
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");
        tenant.setId(1);

        when(tenantService.getTenantById(2)).thenReturn(null);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/tenants/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        );
        verify(tenantService, times(0)).updateTenant(any(Tenant.class));


        when(tenantService.getTenantById(1)).thenReturn(tenant);
        tenant.setPassword("123456");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tenants/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        );
        verify(tenantService, times(1)).updateTenant(any(Tenant.class));

    }

    @Test
    public void addTenantTest() throws Exception{
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");

        LandLord l1 = new LandLord();
        l1.setFirstName("arvine");
        when(landLordService.insertLandLord(l1)).thenReturn(l1);
        l1.setId(1);
        int landLordId = 1;

        when(landLordService.getLandLordById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tenants/add")
                        .param("id",String.valueOf(landLordId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        );

        verify(tenantService, times(0)).insert(any(Tenant.class));

        when(landLordService.getLandLordById(landLordId)).thenReturn(l1);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/tenants/add")
                        .param("id",String.valueOf(landLordId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        tenant.setLandLord(l1);
        verify(tenantService, times(1)).insert(any(Tenant.class));

    }


    @Test
    public void addNewLandlordToTenantTest() throws Exception{
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");

        LandLord l1 = new LandLord();
        l1.setFirstName("george");
        l1.setEmail("george@email.com");
        int landLordId = 1;
        int tenantId = 1;

        when(landLordService.getLandLordById(2)).thenReturn(null);
        when(tenantService.getTenantById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tenants/addNewLandlord")
                        .param("landLordId",String.valueOf(landLordId))
                        .param("tenantId", String.valueOf(tenantId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        );

        verify(tenantService, times(0)).updateTenant(tenant);


        when(landLordService.getLandLordById(landLordId)).thenReturn(l1);
        when(tenantService.getTenantById(tenantId)).thenReturn(tenant);
        tenant.setLandLord(l1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tenants/addNewLandlord")
                        .param("landLordId",String.valueOf(landLordId))
                        .param("tenantId", String.valueOf(tenantId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        );

        verify(tenantService, times(1)).updateTenant(tenant);

    }

    @Test
    public void getLandlordIdByTenantIdTest() throws Exception{
        int tenantId = 1;
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");

        LandLord l1 = new LandLord();
        l1.setFirstName("george");
        l1.setEmail("george@email.com");
        l1.setId(1);
        tenant.setId(1);
        tenant.setLandLord(l1);

        when(tenantService.getTenantById(2)).thenReturn(null);

        MvcResult badResult =  mockMvc.perform(
                MockMvcRequestBuilders.get("/tenants/landlordId")
                        .param("id", String.valueOf(tenantId))
        ).andReturn();

        String badContent = badResult.getResponse().getContentAsString();

        Assert.assertEquals(badContent, "tenant not found");


        when(tenantService.getTenantById(tenantId)).thenReturn(tenant);

        MvcResult result =  mockMvc.perform(
                MockMvcRequestBuilders.get("/tenants/landlordId")
                .param("id", String.valueOf(tenantId))
        ).andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertEquals(content, "1");
    }




    @Test
    public void getTenantByIdTest() throws Exception{
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");
        tenant.setId(1);
        tenantService.insert(tenant);
        when(tenantService.getTenantById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tenants/2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(tenantService.getTenantById(1)).thenReturn(tenant);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/tenants/1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("arvine"));
    }


    @Test
    public void getAllActiveTenantsbyLandlordTest() throws Exception{
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");
        tenant.setId(1);

        ArrayList<Tenant> allTenats = new ArrayList<>();
        allTenats.add(tenant);

        LandLord l1 = new LandLord();
        l1.setFirstName("johnny");
        l1.setId(1);
        l1.setTenants(allTenats);

        when(landLordService.getLandLordById(2)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/tenants/allActivebyLandlord")
                .param("id", "2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(landLordService.getLandLordById(1)).thenReturn(l1);

        mockMvc.perform(MockMvcRequestBuilders.get("/tenants/allActivebyLandlord")
                .param("id", "1")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("arvine"));

    }


    @Test
    public void deleteTenantTest() throws Exception{
        Tenant tenant = new Tenant();
        tenant.setFirstName("arvine");
        tenant.setLastName("rastegar");
        tenant.setEmail("arvine@uab.edu");
        tenant.setPassword("arvine620");
        tenant.setPhoneNumber("123456");
        tenant.setId(1);

        Tenant t2 = new Tenant();
        t2.setId(2);
        t2.setFirstName("johnny");

        when(tenantService.getTenantById(3)).thenReturn(null);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tenants/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        when(tenantService.getTenantById(2)).thenReturn(t2);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tenants/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        when(tenantService.getTenantById(1)).thenReturn(tenant);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tenants/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenant))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(tenantService, times(1)).updateTenant(tenant);


    }






}
