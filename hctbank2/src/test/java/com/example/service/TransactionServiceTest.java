package com.example.service;

import com.example.model.AccBalance;
import com.example.model.AccTransaction;
import com.example.model.CustAddress;
import com.example.model.NewTransaction;
import com.example.model.Response.Exceptionresponse;
import com.example.model.Response.TransactionResponse;
import com.example.repo.AccBalanceRepo;
import com.example.repo.TransactionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {


    @Mock
    TransactionRepo transactionRepo;
    @Mock
    AccBalanceRepo accBalanceRepo;
    @InjectMocks
    TransactionService transactionService;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }



    AccTransaction accTransaction = new AccTransaction(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),
            200,0,500.00,new Timestamp(System.currentTimeMillis()));
    UUID ac1Id = UUID.randomUUID();
    double ac1Balance = 1000.0;
    UUID toAccId = UUID.randomUUID();
    NewTransaction newTransaction = new NewTransaction(ac1Id, toAccId,"credit", 200);

    AccBalance ac1 = new AccBalance(ac1Id, ac1Balance);
    AccBalance ac2 = new AccBalance(toAccId, ac1Balance);

    @Test
    public void createTransactionCredit_success() throws Exception {

        Mockito.when(accBalanceRepo.findById(ac1Id)).thenReturn(Optional.of(ac1));
        Mockito.when(accBalanceRepo.findById(toAccId)).thenReturn(Optional.of(ac2));
        NewTransaction newTransaction = new NewTransaction(ac1Id, toAccId, "credit",200.0);

        ResponseEntity<TransactionResponse> actual = transactionService.createTransactionCredit(newTransaction);
        System.out.println(actual.toString());
        assertEquals("Transaction Success", actual.getBody().getMessage());
        assertEquals("HCT200", actual.getBody().getStatusCode());
    }

    @Test
    public void createTransactionCredit_insufficientBalance() throws Exception {

        UUID ac1Id = UUID.randomUUID();
        double ac1Balance = 100.0;
        AccBalance ac1 = new AccBalance(ac1Id, ac1Balance);
        UUID toAccId = UUID.randomUUID();

        Mockito.when(accBalanceRepo.findById(ac1Id)).thenReturn(Optional.of(ac1));

        NewTransaction newTransaction = new NewTransaction(ac1Id, toAccId, "credit",200.0);

        ResponseEntity<TransactionResponse> actual = transactionService.createTransactionCredit(newTransaction);


        assertEquals("Insufficient balance in sender's account", actual.getBody().getMessage());
        assertEquals("hct400", actual.getBody().getStatusCode());

    }

    @Test
    public void createTransactionCredit_senderNotFound() throws Exception {

        UUID ac1Id = UUID.randomUUID();
        Mockito.when(accBalanceRepo.findById(ac1Id)).thenReturn(Optional.empty());
        NewTransaction newTransaction = new NewTransaction(ac1Id, UUID.randomUUID(), "credit",200.0);
        ResponseEntity<TransactionResponse> actual = transactionService.createTransactionCredit(newTransaction);
        assertEquals("Sender account not found", actual.getBody().getMessage());
        assertEquals("hct400", actual.getBody().getStatusCode());
    }
    @Test
    public void createTransactionCredit_ReceiverNotFound() throws Exception {

        UUID ac1Id = UUID.randomUUID();

        Mockito.when(accBalanceRepo.findById(ac1Id)).thenReturn(Optional.ofNullable(ac1));
        Mockito.when(accBalanceRepo.findById(UUID.randomUUID())).thenReturn(Optional.empty());
        NewTransaction newTransaction = new NewTransaction(ac1Id, UUID.randomUUID(), "credit",200.0);


        ResponseEntity<TransactionResponse> actual = transactionService.createTransactionCredit(newTransaction);

        assertEquals("Receiver account not found", actual.getBody().getMessage());
        assertEquals("hct400", actual.getBody().getStatusCode());
    }

    @Test
    void getTransactionByAccId() {
        List<AccTransaction> expected=new ArrayList<>(List.of(accTransaction));
        Mockito.when(transactionRepo.findByAccId(accTransaction.getAcc_id())).thenReturn(expected);

        ResponseEntity responseEntity=transactionService.getTransactionByAccId(accTransaction.getAcc_id());
        List<AccTransaction> actual= (List<AccTransaction>) responseEntity.getBody();
        assertEquals(expected.get(0).getTrans_id(),actual.get(0).getTrans_id());
    }
    @Test
    void getTransactionByAccId_Empty(){
        when(transactionRepo.findByAccId(accTransaction.getAcc_id())).thenReturn(new ArrayList<>());
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse(" details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity responseEntityActual=transactionService.getTransactionByAccId(accTransaction.getAcc_id());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());
    }

    @Test
    void getTransactionByRefId() {
        List<AccTransaction> expected=new ArrayList<>(List.of(accTransaction));
        Mockito.when(transactionRepo.findByRefId(accTransaction.getTrans_refid())).thenReturn(expected);

        ResponseEntity responseEntity=transactionService.getTransactionByRefId(accTransaction.getTrans_refid());
        List<AccTransaction> actual= (List<AccTransaction>) responseEntity.getBody();
        assertEquals(expected.get(0).getTrans_id(),actual.get(0).getTrans_id());

    }
    @Test
    void getTransactionByRefId_Empty(){
        when(transactionRepo.findByRefId(accTransaction.getTrans_refid())).thenReturn(new ArrayList<>());
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse(" details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity responseEntityActual=transactionService.getTransactionByRefId(accTransaction.getTrans_refid());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());
    }

    @Test
    void getTransactionByAccIdRefId() {
        Optional<AccTransaction> optionalTransaction = Optional.of(accTransaction);
        Mockito.when(transactionRepo.findByAccIdRefId(accTransaction.getAcc_id(), accTransaction.getTrans_refid()))
                .thenReturn(accTransaction);
        ResponseEntity<Object> responseEntity=transactionService.getTransactionByAccIdRefId(
                accTransaction.getAcc_id(), accTransaction.getTrans_refid());
        Optional<AccTransaction> actual= (Optional<AccTransaction>) responseEntity.getBody();
        assertEquals(accTransaction.getTrans_id(),actual.get().getTrans_id());
    }
    @Test
    void getTransactionByAccIdRefId_Empty(){
        when(transactionRepo.findByAccIdRefId(accTransaction.getAcc_id(),accTransaction.getTrans_refid())).thenReturn(null);
        ResponseEntity responseEntityExpected=new ResponseEntity<>(new Exceptionresponse(" details not found","hct404"), HttpStatus.NOT_FOUND);
        ResponseEntity responseEntityActual=transactionService.getTransactionByAccIdRefId(accTransaction.getAcc_id(),accTransaction.getTrans_refid());
        assertEquals(responseEntityExpected.getStatusCode(),responseEntityActual.getStatusCode());
    }
}