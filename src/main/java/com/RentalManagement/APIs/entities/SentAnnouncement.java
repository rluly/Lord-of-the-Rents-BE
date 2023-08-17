package com.RentalManagement.APIs.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class SentAnnouncement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String subject;
    private String message;
    private String date;
    private String time;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="landlord_id")
    private LandLord landLord;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="tenant_id")
    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    private boolean tenantIsActive = true;

    private boolean landlordIsActive = true;

    public boolean isTenantIsActive() {
        return tenantIsActive;
    }

    public void setTenantIsActive(boolean tenantIsActive) {
        this.tenantIsActive = tenantIsActive;
    }

    public boolean getLandlordIsActive() {
        return landlordIsActive;
    }

    public void setLandlordIsActive(boolean landlordIsActive) {
        this.landlordIsActive = landlordIsActive;
    }

    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public SentAnnouncement(){};


    public SentAnnouncement(Integer id, String subject, String message, String date, String time, LandLord landLord) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.time = time;
        this.landLord = landLord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LandLord getLandLord() {
        return landLord;
    }

    public void setLandLord(LandLord landLord) {
        this.landLord = landLord;
    }
}
