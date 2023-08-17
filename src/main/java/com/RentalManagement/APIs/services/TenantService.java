package com.RentalManagement.APIs.services;

import com.RentalManagement.APIs.allRepositories.LandLordRespoistory;
import com.RentalManagement.APIs.allRepositories.TenantRepository;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.entities.Tenant;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class TenantService {

    EntityManager em;

    private TenantRepository tenantRepository;
    private LandLordRespoistory landLordRespoistory;

    public TenantService(TenantRepository tenantRepository, LandLordRespoistory landLordRespoistory){
        this.landLordRespoistory = landLordRespoistory;
        this.tenantRepository = tenantRepository;
    }


    public List<Tenant> getAllActiveTenants(){
        return this.tenantRepository.findByIsActive(true);
    }


    public Tenant getTenantById(Integer id){
        if (id == null) return null;
        else {
            return this.tenantRepository.findById(id).orElse(null);
        }
    }

    public List<Tenant> getAllTenants(){
        return this.tenantRepository.findAll();
    }

    public Tenant insert(Tenant tenant){
        return this.tenantRepository.save(tenant);
    }



    public Tenant updateTenant(Tenant tenant){
        if (this.tenantRepository.findById(tenant.getId()).orElse(null)!=null){
            return this.tenantRepository.save(tenant);
        } else return null;

    }

    public List<Tenant> getAllTenantsByLandLordId(Integer landLordId){
        return this.tenantRepository.findAllById(Collections.singleton(landLordId));

    }


    public Tenant getTenantByEmailAndPassword(String email, String password){
        return this.tenantRepository.findByEmailAndPassword(email, password);
    }


    public Tenant getLandTenantByEmail(String email){
        return this.tenantRepository.findByEmail(email);
    }


}
