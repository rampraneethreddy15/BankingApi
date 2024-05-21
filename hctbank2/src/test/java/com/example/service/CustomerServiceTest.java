package com.example.service;

import com.example.model.*;
import com.example.model.Response.CustomerDetailsResponse;
import com.example.model.Response.CustomerResponse;
import com.example.model.Response.Exceptionresponse;
import com.example.repo.AccBalanceRepo;
import com.example.repo.AddressRepo;
import com.example.repo.CusAccMapRepo;
import com.example.repo.CustomerRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.Timestamp;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class  CustomerServiceTest {


    @Mock
    CustomerRepo customerRepo;
    @Mock
    AddressRepo addressRepo;
    @Mock
    AccBalanceRepo accBalanceRepo;
    @Mock
    CusAccMapRepo cusAccMapRepo;



    @InjectMocks
    CustomerService customerService;

    Timestamp currentTimestamp = new Timestamp(new Date().getTime());
    CustAddress ca1=new CustAddress(UUID.randomUUID(),"india","hyd","vce",509123,currentTimestamp);
    CustAddress ca2=new CustAddress(UUID.randomUUID(),"india","hyd","vce",509123,currentTimestamp);
    CustDetails cd1=new CustDetails(UUID.randomUUID(),"sohail",62818037,ca1,"mohammad@gmail",currentTimestamp,currentTimestamp);
    CustDetails cd2=new CustDetails(UUID.randomUUID(),"sai",62818037,ca2,"mohammad@gmail",currentTimestamp,currentTimestamp);
    AccBalance ac1=new AccBalance(UUID.randomUUID(),500.0);
    AccBalance ac2=new AccBalance(UUID.randomUUID(),200);
    CusAccMap cam1=new CusAccMap(1,ac1,cd1);
    CusAccMap cam2=new CusAccMap(2,ac2,cd2);




    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void addCustomers() throws JsonProcessingException {
        CustomerDetailedAddress cda=new CustomerDetailedAddress("ahmed",9999251,"ahmed@gmail","india","hyd","ibrahimbagh",50000);

        CustAddress ca=new CustAddress(UUID.randomUUID(),cda.getCountry(), cda.getCity(), cda.getAddresslane(),cda.getPin(),currentTimestamp);
        CustDetails cd=new CustDetails(UUID.randomUUID(),cda.getName(),cda.getPhone(),ca,cda.getEmail(),currentTimestamp,currentTimestamp);

        ArgumentCaptor<CustAddress> addressCaptor = ArgumentCaptor.forClass(CustAddress.class);
        ArgumentCaptor<CustDetails> detailsCaptor = ArgumentCaptor.forClass(CustDetails.class);
        Mockito.when(addressRepo.save(addressCaptor.capture())).thenReturn(ca);
        Mockito.when(customerRepo.save(detailsCaptor.capture())).thenReturn(cd);

        CustomerResponse expected=new CustomerResponse("ahmed",cd.getCust_id(),UUID.randomUUID(),500.0);
        CustomerResponse cr=customerService.addCustomer(cda);

        assertEquals("ahmed",cr.getName());

        // Verify captured arguments
        CustAddress savedAddress = addressCaptor.getValue();
        assertEquals("india", savedAddress.getCountry());

        // Verify method calls with captured arguments
        verify(addressRepo).save(addressCaptor.getValue()); // Pass captured argument
        verify(customerRepo).save(detailsCaptor.getValue()); // Pass captured argument
    }



    @Test
    public void getCustomers() throws Exception {
        List<CustDetails> customers=new ArrayList<>(Arrays.asList(cd1,cd2));
        when(customerRepo.findAll()).thenReturn(customers);
        when(cusAccMapRepo.findByCustId(cd1.getCust_id())).thenReturn(cam1);
        when(cusAccMapRepo.findByCustId(cd2.getCust_id())).thenReturn(cam2);
        ResponseEntity<Object> responseEntity=customerService.getCustomers();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        List<CustomerDetailsResponse> cdrlist= (List<CustomerDetailsResponse>) responseEntity.getBody();
        assertEquals(2,cdrlist.size());
        assertEquals("sohail",cdrlist.get(0).getName());
        assertEquals("sai",cdrlist.get(1).getName());
    }

    @Test
    public void getCustomers_Exception(){
        when(customerRepo.findAll()).thenThrow(new RuntimeException("occurred"));
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse("customer details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity responseEntityActual=customerService.getCustomers();
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());
    }

    @Test
    public void getCustomersById(){
        when(customerRepo.findById(cd1.getCust_id())).thenReturn(Optional.ofNullable(cd1));
        when(cusAccMapRepo.findByCustId(cd1.getCust_id())).thenReturn(cam1);
        ResponseEntity<Object> responseEntity=customerService.getCustomerById(cd1.getCust_id());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        CustomerDetailsResponse cdr= (CustomerDetailsResponse) responseEntity.getBody();
        assertEquals("sohail",cdr.getName());
        assertEquals(cam1.getAccBalance().getAccId(),cdr.getAcc_id());

    }
    @Test
    public void getCustomerById_Exception(){
        when(customerRepo.findById(cd1.getCust_id())).thenThrow(new RuntimeException("occurred"));
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse("customer details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity responseEntityActual=customerService.getCustomerById(cd1.getCust_id());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());
    }

}