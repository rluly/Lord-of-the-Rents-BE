package com.RentalManagement.APIs.contollers;

import com.RentalManagement.APIs.allRepositories.TaskRepository;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Task;
import com.RentalManagement.APIs.entities.Tenant;
import com.RentalManagement.APIs.services.LandLordService;
import com.RentalManagement.APIs.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(value = {"http://localhost:4200", "http://960176-rental-management.s3-website-us-west-2.amazonaws.com"})
@RestController
@RequestMapping("tasks")
public class TaskController {

    private TaskService taskService;
    private LandLordService landLordService;

    public TaskController(TaskService taskService, LandLordService landLordService){
        this.landLordService = landLordService;
        this.taskService = taskService;
    }


    @GetMapping("all")
    public List<Task> getAllTasks(){
        return this.taskService.getAllTasks();
    }


    @GetMapping("allActive")
    public ResponseEntity<String> getAllActiveTenants(@RequestParam Integer id){
        LandLord foundLandlord = this.landLordService.getLandLordById(id);
        HttpStatus status;
        if(foundLandlord == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Landlord not found",status);
        } else{
            List<Task> allActiveTenants =  foundLandlord.getTasks().stream().filter(task -> task.isActive()==true).collect(Collectors.toList());
            status = HttpStatus.OK;
            return new ResponseEntity(allActiveTenants, status);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id){
        Task foundTask = this.taskService.getTaskById(id);
        HttpStatus status;

        if(foundTask == null){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("task not found",status);
        }else{
            status = HttpStatus.OK;
            return new ResponseEntity(foundTask,status);
        }
    }


    @PostMapping("add")
    public ResponseEntity<String>  postTask(@RequestBody Task task, @RequestParam Integer id){
        LandLord foundLandlord = this.landLordService.getLandLordById(id);
        HttpStatus status;
        if(foundLandlord == null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("landlord with this id not found",status);
        }
        else{
            task.setLandLord(foundLandlord);
            this.taskService.insert(task);
            status = HttpStatus.OK;
            return new ResponseEntity(task,status);
        }
    }



    @DeleteMapping("deactivate")
    public ResponseEntity<String> deactivateTask(@RequestBody Task task){
        Task foundTask = this.taskService.getTaskById(task.getId());
        HttpStatus status;

        if(foundTask == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("task not found",status);
        }
        else if(!foundTask.getTask().equals(task.getTask()))
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity("Inconsistency between payload and persisted entity",status);
        }
        else{
            status = HttpStatus.OK;
            foundTask.setActive(false);
            this.taskService.updateTask(foundTask);
            return new ResponseEntity(status);
        }
    }




}
