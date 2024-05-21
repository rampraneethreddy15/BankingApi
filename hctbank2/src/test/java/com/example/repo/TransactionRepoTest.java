package com.example.repo;

import com.example.model.AccTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionRepoTest {

    @Mock
    TransactionRepo transactionRepo;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByRefId(){
        AccTransaction accTransaction = new AccTransaction(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),
                456.00,0,500.00,new Timestamp(System.currentTimeMillis()));

        List<AccTransaction> accTransactionList = new ArrayList<>();
        accTransactionList.add(accTransaction);

        when(transactionRepo.findByRefId(accTransaction.getTrans_refid())).thenReturn(accTransactionList);

        List<AccTransaction> transactions = transactionRepo.findByRefId(accTransaction.getTrans_refid());

        assertEquals(accTransactionList,transactions);

    }

    @Test
    public void findByAccId(){
        AccTransaction accTransaction = new AccTransaction(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),
                456.00,0,500.00,new Timestamp(System.currentTimeMillis()));
        List<AccTransaction> li=new ArrayList<>(List.of(accTransaction));
        when(transactionRepo.findByAccId(accTransaction.getAcc_id())).thenReturn(li);

        List<AccTransaction> transactions=transactionRepo.findByAccId(accTransaction.getAcc_id());
        assertEquals(li,transactions);

    }
    @Test
    public void findByAccIdRefId(){
        AccTransaction accTransaction = new AccTransaction(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),
                456.00,0,500.00,new Timestamp(System.currentTimeMillis()));
        when(transactionRepo.findByAccIdRefId(accTransaction.getAcc_id(), accTransaction.getTrans_refid())).thenReturn(accTransaction);

        AccTransaction transactions=transactionRepo.findByAccIdRefId(accTransaction.getAcc_id(), accTransaction.getTrans_refid());
        assertEquals(accTransaction,transactions);

    }

}