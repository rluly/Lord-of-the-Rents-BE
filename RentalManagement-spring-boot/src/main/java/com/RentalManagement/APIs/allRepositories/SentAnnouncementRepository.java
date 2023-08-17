package com.RentalManagement.APIs.allRepositories;

import com.RentalManagement.APIs.entities.SentAnnouncement;
import com.RentalManagement.APIs.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentAnnouncementRepository extends JpaRepository<SentAnnouncement, Integer> {

    public List<SentAnnouncement> findByIsActive(boolean isActive);
    public List<SentAnnouncement> findByTenantIsActive(boolean tenantIsActive);
    public List<SentAnnouncement> findByLandlordIsActive(boolean landlordIsActive);
}


