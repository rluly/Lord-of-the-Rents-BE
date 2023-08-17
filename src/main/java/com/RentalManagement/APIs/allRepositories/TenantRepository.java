package com.RentalManagement.APIs.allRepositories;

import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    public List<Tenant> findByIsActive(boolean isActive);

    public Tenant findByEmailAndPassword(String email, String password);

    public Tenant findByEmail(String email);


}
