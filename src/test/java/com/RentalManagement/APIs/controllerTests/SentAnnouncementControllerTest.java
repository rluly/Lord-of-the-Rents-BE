package com.RentalManagement.APIs.controllerTests;

import com.RentalManagement.APIs.contollers.LandLordController;
import com.RentalManagement.APIs.contollers.SentAnnouncementController;
import com.RentalManagement.APIs.contollers.TenantController;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.SentAnnouncement;
import com.RentalManagement.APIs.entities.Task;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.SentAnnouncementService;
import com.RentalManagement.APIs.services.TenantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


@WebMvcTest(SentAnnouncementController.class)
public class SentAnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private LandLordService landLordService;


    @MockBean
    private TenantService tenantService;

    @MockBean
    private SentAnnouncementService sentAnnouncementService;


    @Test
    public void getAllSentAnnouncementsTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/sentAnnouncements/all")
        );
        verify(sentAnnouncementService, times(1)).getAllSentAnnouncements();
    }

    @Test
    public void getAllActiveSentAnnouncementsLandlordsTest() throws Exception{

        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("do something");
        msg.setId(1);


        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");
        landLord.setId(1);
        msg.setLandLord(landLord);
        ArrayList<SentAnnouncement> allmsgs = new ArrayList<>();

        allmsgs.add(msg);
        landLord.setSentAnncouncements(allmsgs);

        when(landLordService.getLandLordById(2)).thenReturn(null);


        mockMvc.perform(
                MockMvcRequestBuilders.get("/sentAnnouncements/allActiveLandlords")
                .param("id", "2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(landLordService.getLandLordById(1)).thenReturn(landLord);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/sentAnnouncements/allActiveLandlords")
                        .param("id", "1")

        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("do something"));



    }


    @Test
    public void getAllActiveSentAnnouncementsTenantsTest() throws Exception{

        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("do something");
        msg.setId(1);


        Tenant t1 = new Tenant();
        t1.setId(1);
        t1.setFirstName("arvine");

        msg.setTenant(t1);
        ArrayList<SentAnnouncement> allmsgs = new ArrayList<>();

        allmsgs.add(msg);
        t1.setSentAnnouncements(allmsgs);

        when(tenantService.getTenantById(2)).thenReturn(null);


        mockMvc.perform(
                MockMvcRequestBuilders.get("/sentAnnouncements/allActiveTenants")
                        .param("id", "2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(tenantService.getTenantById(1)).thenReturn(t1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/sentAnnouncements/allActiveTenants")
                        .param("id", "1")

        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("do something"));



    }







    @Test
    public void getSentAnnouncementByIdTest() throws Exception{
        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("do something");
        msg.setId(1);
        sentAnnouncementService.insert(msg);
        when(sentAnnouncementService.getSentAnnouncementsById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/sentAnnouncements/2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(sentAnnouncementService.getSentAnnouncementsById(1)).thenReturn(msg);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sentAnnouncements/1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("do something"));
    }



    @Test
    public void addSentAnnouncementTest() throws Exception{
        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("do something");

        Tenant t1 = new Tenant();
        t1.setFirstName("john");
        t1.setId(1);


        LandLord l1 = new LandLord();
        l1.setFirstName("arvine");
        when(landLordService.insertLandLord(l1)).thenReturn(l1);
        l1.setId(1);
        int landLordId = 1;
        int tenantId = 1;
        t1.setLandLord(l1);

        when(landLordService.getLandLordById(2)).thenReturn(null);
        when(tenantService.getTenantById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/sentAnnouncements/add")
                        .param("landlordId",String.valueOf(landLordId))
                        .param("tenantId", String.valueOf(tenantId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(sentAnnouncementService, times(0)).insert(any(SentAnnouncement.class));

        when(landLordService.getLandLordById(landLordId)).thenReturn(l1);
        when(tenantService.getTenantById(tenantId)).thenReturn(t1);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/sentAnnouncements/add")
                        .param("landlordId",String.valueOf(landLordId))
                        .param("tenantId", String.valueOf(tenantId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        t1.setLandLord(l1);
        verify(sentAnnouncementService, times(1)).insert(any(SentAnnouncement.class));

    }


    @Test
    public void DeleteSentAnnouncementsLandlordSideTest() throws Exception{
        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("do something");
        msg.setId(1);

        SentAnnouncement msg2 = new SentAnnouncement();
        msg2.setMessage("do something else");
        msg2.setId(2);

        when(sentAnnouncementService.getSentAnnouncementsById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/sentAnnouncements/deactivateLandlord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(sentAnnouncementService.getSentAnnouncementsById(1)).thenReturn(msg2);
        MvcResult badresult = mockMvc.perform(
                MockMvcRequestBuilders.delete("/sentAnnouncements/deactivateLandlord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        String badContent = badresult.getResponse().getContentAsString();

        Assert.assertEquals(badContent,"Inconsistency between payload and persisted entity");




        when(sentAnnouncementService.getSentAnnouncementsById(1)).thenReturn(msg);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/sentAnnouncements/deactivateLandlord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(sentAnnouncementService, times(1)).updateSentAnnouncement(msg);

        String content = result.getResponse().getContentAsString();
    }


    @Test
    public void DeleteSentAnnouncementsTenantSideTest() throws Exception{
        SentAnnouncement msg = new SentAnnouncement();
        msg.setMessage("do something");
        msg.setId(1);

        SentAnnouncement msg2 = new SentAnnouncement();
        msg2.setMessage("do something else");
        msg2.setId(2);

        when(sentAnnouncementService.getSentAnnouncementsById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/sentAnnouncements/deactivateTenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(sentAnnouncementService.getSentAnnouncementsById(1)).thenReturn(msg2);
        MvcResult badresult = mockMvc.perform(
                MockMvcRequestBuilders.delete("/sentAnnouncements/deactivateTenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        String badContent = badresult.getResponse().getContentAsString();

        Assert.assertEquals(badContent,"Inconsistency between payload and persisted entity");




        when(sentAnnouncementService.getSentAnnouncementsById(1)).thenReturn(msg);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/sentAnnouncements/deactivateTenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();


        verify(sentAnnouncementService, times(1)).updateSentAnnouncement(msg);

    }








}
