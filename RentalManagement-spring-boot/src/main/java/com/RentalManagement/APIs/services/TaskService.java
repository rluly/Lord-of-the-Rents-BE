package com.RentalManagement.APIs.services;


import com.RentalManagement.APIs.allRepositories.TaskRepository;
import com.RentalManagement.APIs.contollers.TaskController;
import com.RentalManagement.APIs.entities.Task;
import com.RentalManagement.APIs.entities.Tenant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllActiveTasks(){
        return this.taskRepository.findByIsActive(true);
    }


    public List<Task> getAllTasks(){
        return this.taskRepository.findAll();
    }

    public Task getTaskById(Integer id){
        if (id == null) return null;
        else {
            return this.taskRepository.findById(id).orElse(null);
        }
    }


    public Task insert(Task task){
        return this.taskRepository.save(task);
    }


    public Task updateTask(Task task){
        if (this.taskRepository.findById(task.getId()).orElse(null)!=null){
            return this.taskRepository.save(task);
        } else return null;

    }






}
