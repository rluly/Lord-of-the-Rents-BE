package com.RentalManagement.APIs.allRepositories;

import com.RentalManagement.APIs.entities.Task;
import com.RentalManagement.APIs.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> findByIsActive(boolean isActive);
}


