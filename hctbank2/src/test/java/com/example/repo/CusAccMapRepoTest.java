package com.example.repo;

import com.example.model.AccBalance;
import com.example.model.CusAccMap;
import com.example.model.CustAddress;
import com.example.model.CustDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CusAccMapRepoTest {

    @Mock
    CusAccMapRepo cusAccMapRepo;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByCustId() {
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        CustAddress ca1=new CustAddress(UUID.randomUUID(),"india","hyd","vce",509123,currentTimestamp);
        CustDetails cd1=new CustDetails(UUID.randomUUID(),"sai",62818037,ca1,"mohammad@gmail",currentTimestamp,currentTimestamp);
        AccBalance ac1=new AccBalance(UUID.randomUUID(),500.0);

        CusAccMap cusAccMap=new CusAccMap(5,ac1,cd1);
        when(cusAccMapRepo.findByCustId(cd1.getCust_id())).thenReturn(cusAccMap);
        CusAccMap result=cusAccMapRepo.findByCustId(cd1.getCust_id());
        assertEquals(cusAccMap,result);

    }
}