package com.RentalManagement.APIs.serviceTests;

import com.RentalManagement.APIs.allRepositories.LandLordRespoistory;
import com.RentalManagement.APIs.entities.LandLord;
import com.RentalManagement.APIs.services.LandLordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class LandlordServiceTests {

    @Mock
    private LandLordRespoistory landLordRespoistory;

    @InjectMocks
    private LandLordService landLordService;


    @Test
    public void getAllActiveLandLordsTest() {
        landLordService.getAllActiveLandLords();
        verify(landLordRespoistory, times(1)).findByIsActive(true);
    }

    @Test
    public void getAllLandLordsTest(){
        landLordService.getAllLandLords();
        verify(landLordRespoistory, times(1)).findAll();
    }


    @Test
    public void getLandLordByIdTest(){
        int paramId = 1;
        landLordService.getLandLordById(paramId);
        verify(landLordRespoistory, times(1)).findById(paramId);
    }

    @Test
    public void insertLandLordTest(){
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");

        landLordService.insertLandLord(landLord);
        verify(landLordRespoistory, times(1)).save(landLord);
    }


    @Test
    public void updateLandLordTest(){
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");

        landLordService.insertLandLord(landLord);

        int paramId = 1;
        landLordService.getLandLordById(paramId);
        verify(landLordRespoistory, times(1)).findById(paramId);

        landLordService.updateLandLord(landLord);
        verify(landLordRespoistory, times(1)).save(landLord);
    }

    @Test
    public void getLandlordByEmailTest(){
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");

        landLordService.insertLandLord(landLord);

        landLordService.getLandlordByEmail(landLord.getEmail());

        verify(landLordRespoistory, times(1)).findByEmail(landLord.getEmail());

    }

    @Test
    public void getLandlordByEmailAndPasswordTest(){
        LandLord landLord = new LandLord();
        landLord.setFirstName("arvine");
        landLord.setLastName("rastegar");
        landLord.setEmail("arvine@uab.edu");
        landLord.setPassword("arvine620");
        landLord.setPhoneNumber("123456");

        landLordService.insertLandLord(landLord);

        landLordService.getLandlordByEmailAndPassword(landLord.getEmail(),landLord.getPassword());
        verify(landLordRespoistory, times(1)).findByEmailAndPassword(landLord.getEmail(),landLord.getPassword());
    }




}
