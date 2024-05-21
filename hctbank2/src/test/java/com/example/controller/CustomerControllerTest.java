package com.example.controller;

import com.example.model.*;
import com.example.model.Response.BalanceResponse;
import com.example.model.Response.CustomerResponse;
import com.example.model.Response.TransactionResponse;
import com.example.repo.CustomerRepo;
import com.example.service.BalanceService;
import com.example.service.CustomerService;
import com.example.service.TransactionService;
import com.example.service.UpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

 private MockMvc mockMvc;

    @Mock
    CustomerService customerService;
    @Mock
    BalanceService balanceService;
    @Mock
    TransactionService transactionService;
    @Mock
    UpdateService updateService;


    @InjectMocks
    CustomerController customerController;

    @BeforeEach
    public void setup(){
        this.mockMvc= MockMvcBuilders.standaloneSetup(customerController).build();
    }


    UUID id1=UUID.randomUUID();
    UUID id2=UUID.randomUUID();
    UUID id3=UUID.randomUUID();
    UUID id4=UUID.randomUUID();
    UUID id5=UUID.randomUUID();
    UUID id6=UUID.randomUUID();
    ObjectMapper mapper = new ObjectMapper();
    Timestamp currentTimestamp = new Timestamp(new Date().getTime());
    CustAddress ca1=new CustAddress(id1,"india","hyd","vce",509123,currentTimestamp);
    CustAddress ca2=new CustAddress(id2,"india","hyd","vce",509123,currentTimestamp);
    CustDetails cd1=new CustDetails(id3,"sohail",62818037,ca1,"mohammad@gmail",currentTimestamp,currentTimestamp);
    CustDetails cd2=new CustDetails(id4,"sai",62818037,ca2,"mohammad@gmail",currentTimestamp,currentTimestamp);
    AccBalance ac1=new AccBalance(id5,500.0);
    AccBalance ac2=new AccBalance(id6,200);
    CusAccMap cam1=new CusAccMap(1,ac1,cd1);
    CusAccMap cam2=new CusAccMap(2,ac2,cd2);

    @Test
    public void getAllCustomers() throws Exception {
        List<CustDetails> li=new ArrayList<>(Arrays.asList(cd1,cd2));
        ResponseEntity<Object> responseEntity=new ResponseEntity<>(li, HttpStatus.OK);
        when(customerService.getCustomers()).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/hct/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name",is("sai")));

    }
    @Test
   public void getCustomerById() throws Exception {
       ResponseEntity<Object> responseEntity=new ResponseEntity<>(cd1, HttpStatus.OK);
       when(customerService.getCustomerById(id3)).thenReturn(responseEntity);
       mockMvc.perform(MockMvcRequestBuilders.get("/hct/customers/{id3}",id3)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("sohail")));

    }

    

    @Test
    public void getBalance_byCustId() throws Exception {
        UUID cust_id=UUID.randomUUID();
        BalanceResponse expected=new BalanceResponse(id3,200);
        ResponseEntity<Object> responseEntity=new ResponseEntity<>(expected,HttpStatus.OK);
        Mockito.when(balanceService.getBalanceByCustId(cust_id)).thenReturn(responseEntity);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/hct/balances")
                .param("cust_id",String.valueOf(cust_id)))
                .andExpect(status().isOk())
                .andReturn();
        String response=result.getResponse().getContentAsString();
        BalanceResponse actual=mapper.readValue(response,BalanceResponse.class);
        assertEquals(expected.getAcc_id(),actual.getAcc_id());
        assertEquals(expected.getAvlbalance(),actual.getAvlbalance());

    }
    @Test
    public void getBalance_byAccId() throws Exception {
        UUID acc_id=UUID.randomUUID();
        BalanceResponse expected=new BalanceResponse(id5,200);
        ResponseEntity<Object> responseEntity=new ResponseEntity<>(expected,HttpStatus.OK);
        Mockito.when(balanceService.getBalanceByAccId(acc_id)).thenReturn(responseEntity);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/hct/balances")
                        .param("acc_id",String.valueOf(acc_id)))
                .andExpect(status().isOk())
                .andReturn();
        String response=result.getResponse().getContentAsString();
        BalanceResponse actual=mapper.readValue(response,BalanceResponse.class);
        assertEquals(expected.getAcc_id(),actual.getAcc_id());
        assertEquals(expected.getAvlbalance(),actual.getAvlbalance());

    }
    
    @Test
    public void createTransaction() throws Exception {
        NewTransaction newTransaction=new NewTransaction(id5,id6,"credit",200.0);
        TransactionResponse expected=new TransactionResponse("Transaction Success","hct200",UUID.randomUUID());
        ResponseEntity responseEntity=new ResponseEntity<>(expected,HttpStatus.OK);
        when(transactionService.createTransactionCredit(Mockito.any())).thenReturn(responseEntity);
        String content=mapper.writeValueAsString(newTransaction);
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/hct/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andReturn();
        String response=result.getResponse().getContentAsString();
        TransactionResponse actual=mapper.readValue(response,TransactionResponse.class);
        assertEquals(expected.getMessage(),actual.getMessage());

    }
    @Test
    public void createTransaction_InvalidType() throws Exception {
        NewTransaction newTransaction=new NewTransaction(id5,id6,"anything",200.0);
        //TransactionResponse expected=new TransactionResponse("invalid details","Hct404",0);
        String content=mapper.writeValueAsString(newTransaction);
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/hct/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void getTransaction_NoArguments() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.get("/hct/transactions"))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getTransaction_ByAccId() throws Exception {
        UUID acc_id=UUID.randomUUID();
        AccTransaction accTransaction = new AccTransaction(UUID.randomUUID(),UUID.randomUUID(),acc_id,
                456.00,0,500.00,new Timestamp(System.currentTimeMillis()));
        ResponseEntity<Object> responseEntity=new ResponseEntity<>(accTransaction,HttpStatus.OK);
        Mockito.when(transactionService.getTransactionByAccId(acc_id)).thenReturn(responseEntity);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/hct/transactions")
                        .param("acc_id",String.valueOf(acc_id)))
                .andExpect(status().isOk())
                .andReturn();
        String response=result.getResponse().getContentAsString();
        AccTransaction actual=mapper.readValue(response,AccTransaction.class);
        assertEquals(accTransaction.getTrans_id(),actual.getTrans_id());

    }

    @Test
    public void getTransaction_ByRefId() throws Exception {
        UUID ref_id=UUID.randomUUID();
        AccTransaction accTransaction = new AccTransaction(UUID.randomUUID(),ref_id,UUID.randomUUID(),
                456.00,0,500.00,new Timestamp(System.currentTimeMillis()));
        ResponseEntity<Object> responseEntity=new ResponseEntity<>(accTransaction,HttpStatus.OK);

        Mockito.when(transactionService.getTransactionByRefId(ref_id)).thenReturn(responseEntity);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/hct/transactions")
                        .param("ref_id",String.valueOf(ref_id)))
                .andExpect(status().isOk())
                .andReturn();
        String response=result.getResponse().getContentAsString();
        AccTransaction actual=mapper.readValue(response,AccTransaction.class);
        assertEquals(accTransaction.getTrans_id(),actual.getTrans_id());

    }
    @Test
    public void getTransaction_ByAccIdRefId() throws Exception {
        UUID acc_id=UUID.randomUUID();
        UUID ref_id=UUID.randomUUID();
        AccTransaction accTransaction = new AccTransaction(UUID.randomUUID(),ref_id,acc_id,
                456.00,0,500.00,new Timestamp(System.currentTimeMillis()));
        ResponseEntity<Object> responseEntity=new ResponseEntity<>(accTransaction,HttpStatus.OK);
        Mockito.when(transactionService.getTransactionByAccIdRefId(acc_id,ref_id)).thenReturn(responseEntity);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/hct/transactions")
                        .param("acc_id",String.valueOf(acc_id))
                        .param("ref_id",String.valueOf(ref_id)))
                .andExpect(status().isOk())
                .andReturn();
        String response=result.getResponse().getContentAsString();
        AccTransaction actual=mapper.readValue(response,AccTransaction.class);
        assertEquals(accTransaction.getTrans_id(),actual.getTrans_id());

    }

    




}