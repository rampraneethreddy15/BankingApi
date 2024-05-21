package com.example.service;

import com.example.model.AccBalance;
import com.example.model.CusAccMap;
import com.example.model.CustAddress;
import com.example.model.CustDetails;
import com.example.model.Response.BalanceResponse;
import com.example.model.Response.Exceptionresponse;
import com.example.repo.AccBalanceRepo;
import com.example.repo.CusAccMapRepo;
import com.example.repo.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceTest {

    @Mock
    AccBalanceRepo accBalanceRepo;
    @Mock
    CusAccMapRepo cusAccMapRepo;

    @InjectMocks
    BalanceService balanceService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    Timestamp currentTimestamp = new Timestamp(new Date().getTime());
    CustAddress ca1=new CustAddress(UUID.randomUUID(),"india","hyd","vce",509123,currentTimestamp);
    CustDetails cd1=new CustDetails(UUID.randomUUID(),"sohail",62818037,ca1,"mohammad@gmail",currentTimestamp,currentTimestamp);
    AccBalance ac1=new AccBalance(UUID.randomUUID(),500.0);
    CusAccMap cam1=new CusAccMap(1,ac1,cd1);
    BalanceResponse expected=new BalanceResponse(ac1.getAccId(), ac1.getBalance());

    @Test
    void getBalanceByCustId() {
        Mockito.when(cusAccMapRepo.findByCustId(cd1.getCust_id())).thenReturn(cam1);
        Mockito.when(accBalanceRepo.findById(ac1.getAccId())).thenReturn(Optional.ofNullable(ac1));
        ResponseEntity<Object> responseEntity=balanceService.getBalanceByCustId(cd1.getCust_id());
        BalanceResponse actual= (BalanceResponse) responseEntity.getBody();
        assertEquals(expected.getAcc_id(),actual.getAcc_id());

    }
    @Test
    void getBalanceByCustId_NotFound(){
        Mockito.when(cusAccMapRepo.findByCustId(cd1.getCust_id())).thenReturn(cam1);
        Mockito.when(accBalanceRepo.findById(ac1.getAccId())).thenReturn(Optional.empty());
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse("account details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity<Object> responseEntityActual=balanceService.getBalanceByCustId(cd1.getCust_id());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());

    }
    @Test
    void getBalanceByCustId_Exception(){
        Mockito.when(cusAccMapRepo.findByCustId(cd1.getCust_id())).thenReturn(cam1);
        Mockito.when(accBalanceRepo.findById(ac1.getAccId())).thenThrow(new RuntimeException("occured"));
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse("account details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity<Object> responseEntityActual=balanceService.getBalanceByCustId(cd1.getCust_id());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());

    }


    @Test
    void getBalanceByAccId() {

        Mockito.when(accBalanceRepo.findById(ac1.getAccId())).thenReturn(Optional.ofNullable(ac1));
        ResponseEntity<Object> responseEntity=balanceService.getBalanceByAccId(ac1.getAccId());
        BalanceResponse actual= (BalanceResponse) responseEntity.getBody();
        assertEquals(expected.getAcc_id(),actual.getAcc_id());
    }
    @Test
    void getBalanceByAccId_NotFound(){
        Mockito.when(accBalanceRepo.findById(ac1.getAccId())).thenReturn(Optional.empty());
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse("account details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity<Object> responseEntityActual=balanceService.getBalanceByAccId(cd1.getCust_id());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());

    }
    @Test
    void getBalanceByAccId_Exception(){
        Mockito.when(accBalanceRepo.findById(ac1.getAccId())).thenThrow(new RuntimeException("occurred"));
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse("customer details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity<Object> responseEntityActual=balanceService.getBalanceByAccId(ac1.getAccId());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());

    }
}