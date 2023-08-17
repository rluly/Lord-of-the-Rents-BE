package com.RentalManagement.APIs.controllerTests;

import com.RentalManagement.APIs.contollers.LandLordController;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TenantService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@WebMvcTest(LandLordController.class)
public class LandlordControllerTest {

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
    public void postLandlord_callServiceSave() throws Exception{
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/landlords/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landLord))
        );
        verify(landLordService, times(1)).insertLandLord(any(LandLord.class));

    }


    @Test
    public void getAllLandLordsTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/landlords/all")
        );
        verify(landLordService, times(1)).getAllLandLords();
    }

    @Test
    public void getAllActiveLandLordsTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/landlords/allActive")
        );
        verify(landLordService, times(1)).getAllActiveLandLords();
    }

    @Test
    public void updateLandLordsTest() throws Exception{
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");
        landLord.setId(1);

        when(landLordService.getLandLordById(2)).thenReturn(null);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/landlords/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landLord))
        );
        verify(landLordService, times(0)).updateLandLord(any(LandLord.class));


        when(landLordService.getLandLordById(1)).thenReturn(landLord);
        landLord.setPassword("123456");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/landlords/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landLord))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        verify(landLordService, times(1)).updateLandLord(any(LandLord.class));

    }

    @Test
    public void getLandLordByIdTest() throws Exception{
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");
        landLord.setId(1);
        when(landLordService.getLandLordById(landLord.getId())).thenReturn(landLord);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/landlords/1")
        ).andExpect(MockMvcResultMatchers.status().isOk());
        verify(landLordService, times(1)).getLandLordById(1);

    }

    @Test
    public void deleteLandLordTest() throws  Exception {
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");
        landLord.setId(1);

        LandLord l2 = new LandLord();
        l2.setId(2);
        l2.setFirstName("johnny");

        Tenant t1 = new Tenant();
        Tenant t2 = new Tenant();
        ArrayList<Tenant> allTenants = new ArrayList<>();
        allTenants.add(t1);
        allTenants.add(t2);

        landLord.setTenants(allTenants);

        t1.setLandLord(landLord);
        t2.setLandLord(landLord);

        when(landLordService.getLandLordById(2)).thenReturn(null);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/landlords/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landLord))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
        verify(landLordService, times(0)).updateLandLord(any(LandLord.class));


        when(landLordService.getLandLordById(1)).thenReturn(l2);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/landlords/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landLord))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
        verify(landLordService, times(0)).updateLandLord(any(LandLord.class));


        when(landLordService.getLandLordById(landLord.getId())).thenReturn(landLord);


       mockMvc.perform(
                MockMvcRequestBuilders.delete("/landlords/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landLord))
        ).andExpect(MockMvcResultMatchers.status().isOk());


        verify(landLordService, times(1)).updateLandLord(any(LandLord.class));

    }


    @Test
    public void getLandLordOrTenantByEmailTest() throws Exception{
        Tenant t1 = new Tenant();
        t1.setFirstName("john");
        t1.setEmail("john@email.com");
        t1.setPhoneNumber("22232323");
        t1.setId(1);

        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");
        landLord.setId(1);
        t1.setLandLord(landLord);
        String paramEmail = "arvine@uab.edu";


        mockMvc.perform(MockMvcRequestBuilders.get("/landlords/loginByEmail")
                .param("email", paramEmail)
        );

        verify(landLordService, times(1)).getLandlordByEmail(paramEmail);
        verify(tenantService, times(1)).getLandTenantByEmail(paramEmail);




    }


    @Test
    public void getLandlordByIdTest() throws  Exception{
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");
        landLord.setId(1);
        landLordService.insertLandLord(landLord);
        when(landLordService.getLandLordById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/landlords/2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(landLordService.getLandLordById(1)).thenReturn(landLord);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/landlords/1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("arvine"));
    }




    // use for reference:









}
