package com.example.service;

import com.example.model.CustAddress;
import com.example.model.CustDetails;
import com.example.model.CustomerDetailedAddress;
import com.example.repo.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UpdateServiceTest {


    @Mock
    CustomerRepo customerRepo;

    @InjectMocks
    UpdateService updateService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    CustomerDetailedAddress cda=
            new CustomerDetailedAddress
                    ("sohail",9999251,"ahmed@gmail","india","hyd","ibrahimbagh",50000);

    Timestamp currentTimestamp = new Timestamp(new Date().getTime());
    CustAddress ca=new CustAddress(UUID.randomUUID(),cda.getCountry(), cda.getCity(), cda.getAddresslane(),cda.getPin(),currentTimestamp);
    CustDetails cd=new CustDetails(UUID.randomUUID(),"ahmed",cda.getPhone(),ca,cda.getEmail(),currentTimestamp,currentTimestamp);
    @Test
    void updateCustomerDetails() {

        // long cust_id=126L;
        Mockito.when(customerRepo.findById(cd.getCust_id())).thenReturn(Optional.ofNullable(cd));
        String msg= updateService.updateCustomerDetails(cd.getCust_id(),cda);
        assertEquals("sohail",cd.getName());
        assertEquals("updated",msg);

    }
}