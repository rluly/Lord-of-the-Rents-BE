package com.RentalManagement.APIs.allRepositories;

import com.RentalManagement.APIs.entities.LandLord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LandLordRespoistory extends JpaRepository<LandLord, Integer> {

    public List<LandLord> findByIsActive(boolean isActive);

    public LandLord findTheLandLordById(Integer id);

    public LandLord findByEmailAndPassword(String email, String password);

    public LandLord findByEmail(String email);



}
