package com.RentalManagement.APIs.services;

import com.RentalManagement.APIs.allRepositories.LandLordRespoistory;
import com.RentalManagement.APIs.entities.LandLord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LandLordService {

    private LandLordRespoistory landLordRespoistory;

    public LandLordService(LandLordRespoistory landLordRespoistory){
        this.landLordRespoistory = landLordRespoistory;
    }


    public List<LandLord> getAllActiveLandLords(){
        return this.landLordRespoistory.findByIsActive(true);
    }

    public List<LandLord> getAllLandLords(){
        return this.landLordRespoistory.findAll();
    }

    public LandLord getLandLordById(Integer id){
        if (id == null) return null;
        else
        return this.landLordRespoistory.findById(id).orElse(null);
    }

    public LandLord insertLandLord(LandLord landlord){
        return this.landLordRespoistory.save(landlord);
    }

    // possibly remove this keyword?
    public LandLord updateLandLord(LandLord landlord){
        if (this.landLordRespoistory.findById(landlord.getId()).orElse(null)!=null){
            return this.landLordRespoistory.save(landlord);
        } else return null;

    }


    public LandLord getLandlordByEmailAndPassword(String email, String password){
        return this.landLordRespoistory.findByEmailAndPassword(email, password);
    }

    public LandLord getLandlordByEmail(String email){
        return this.landLordRespoistory.findByEmail(email);
    }







}
