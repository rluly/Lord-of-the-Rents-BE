package com.RentalManagement.APIs.entities;


import javax.persistence.*;
import java.util.List;

@Entity
public class LandLord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private  String lastName;
    private String email;
    private String phoneNumber;
    private int accountNumber;
    private int routingNumber;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    //    @OneToMany(targetEntity = Tenant.class)
//    @JoinColumn(name = "tenantfk", referencedColumnName = "id")\

    // landlord = variable name given within the tenant class;
    //@OneToMany(mappedBy = "landlord")
    @OneToMany(mappedBy = "landLord", cascade = CascadeType.ALL)
    private List<Tenant> tenants;

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants){
        this.tenants = tenants;
    }

    public void addTenants(Tenant tenants) {
        this.tenants.add(tenants);
    }

    @OneToMany(mappedBy = "landLord", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<SentAnnouncement> getSentAnncouncements() {
        return sentAnncouncements;
    }

    public void setSentAnncouncements(List<SentAnnouncement> sentAnncouncements) {
        this.sentAnncouncements = sentAnncouncements;
    }

    @OneToMany(mappedBy = "landLord", cascade = CascadeType.ALL)
    private List<SentAnnouncement> sentAnncouncements;

    public LandLord(){};

    public LandLord(Integer id, String firstName, String lastName, String email, String phoneNumber, int accountNumber, int routingNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }
}
