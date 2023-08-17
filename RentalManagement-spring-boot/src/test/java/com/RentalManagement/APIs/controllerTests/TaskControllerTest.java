package com.RentalManagement.APIs.controllerTests;

import com.RentalManagement.APIs.contollers.TaskController;
import com.RentalManagement.APIs.contollers.TenantController;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Task;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    //
    @MockBean
    private LandLordService landLordService;

    @MockBean
    private TaskService taskService;


    @Test
    public void getAllTasksTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/all")
        );
        verify(taskService, times(1)).getAllTasks();
    }


    // look over this one again to get full coverage!
    @Test
    public void getAllActiveTasksTest() throws Exception{
        LandLord l1 = new LandLord();
        l1.setId(1);
        l1.setFirstName("arvine");

        Task t1 = new Task();
        t1.setTask("do something");
        t1.setId(1);

        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.add(t1);

        l1.setTasks(allTasks);

        when(landLordService.getLandLordById(2)).thenReturn(null);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/allActive")
                .param("id", "2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        when(landLordService.getLandLordById(1)).thenReturn(l1);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/allActive")
                        .param("id", "1")
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    public void getTaskByIdTest() throws Exception{
        Task t1 = new Task();
        t1.setTask("do something");
        t1.setId(1);
        taskService.insert(t1);
        when(taskService.getTaskById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/2")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(taskService.getTaskById(1)).thenReturn(t1);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/tasks/1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.task").value("do something"));
    }


    @Test
    public void addTaskTest() throws Exception{
        Task t1 = new Task();
        t1.setTask("do something");
        t1.setId(1);

        LandLord l1 = new LandLord();
        l1.setFirstName("arvine");
        when(landLordService.insertLandLord(l1)).thenReturn(l1);
        l1.setId(1);
        int landLordId = 1;

        when(landLordService.getLandLordById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks/add")
                        .param("id",String.valueOf(landLordId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t1))
        );

        verify(taskService, times(0)).insert(any(Task.class));

        when(landLordService.getLandLordById(landLordId)).thenReturn(l1);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks/add")
                        .param("id",String.valueOf(landLordId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t1))
        );
        t1.setLandLord(l1);
        verify(taskService, times(1)).insert(any(Task.class));

    }


    @Test
    public void deleteTaskTest() throws Exception{
        Task t1 = new Task();
        t1.setTask("do something");
        t1.setId(1);

        Task t2 = new Task();
        t2.setTask("do something else");
        t2.setId(2);

        when(taskService.getTaskById(2)).thenReturn(null);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t1))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());


        when(taskService.getTaskById(t1.getId())).thenReturn(t2);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t1))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertEquals(content, "Inconsistency between payload and persisted entity");

        when(taskService.getTaskById(1)).thenReturn(t1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t1))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(taskService, times(1)).updateTask(t1);





    }





}
