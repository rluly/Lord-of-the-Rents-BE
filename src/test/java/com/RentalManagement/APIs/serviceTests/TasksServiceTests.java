package com.RentalManagement.APIs.serviceTests;

import com.RentalManagement.APIs.allRepositories.LandLordRespoistory;
import com.RentalManagement.APIs.allRepositories.TaskRepository;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Task;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class TasksServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void getAllActiveTasksTest() {
        taskService.getAllActiveTasks();
        verify(taskRepository, times(1)).findByIsActive(true);
    }

    @Test
    public void getAllTasksTest(){
        taskService.getAllTasks();
        verify(taskRepository, times(1)).findAll();
    }


    @Test
    public void getTaskByIdTest(){
        int paramId = 1;
        taskService.getTaskById(paramId);
        verify(taskRepository, times(1)).findById(paramId);
    }

    @Test
    public void insertTaskTest(){
        Task t1 = new Task();
        t1.setTask("do something");
        taskService.insert(t1);
        verify(taskRepository, times(1)).save(t1);
    }


    @Test
    public void updateTaskTest(){
        Task t1 = new Task();
        t1.setTask("do something");

        taskService.insert(t1);

        int paramId = 1;
        taskService.getTaskById(paramId);
        verify(taskRepository, times(1)).findById(paramId);

        taskService.updateTask(t1);
        verify(taskRepository, times(1)).save(t1);
    }






}
