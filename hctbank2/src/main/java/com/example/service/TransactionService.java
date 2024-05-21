package com.example.service;

import com.example.model.AccBalance;
import com.example.model.AccTransaction;
import com.example.model.NewTransaction;
import com.example.model.Response.Exceptionresponse;
import com.example.model.Response.TransactionResponse;
import com.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CustomerAddressRepo customerAddressRepo;
    @Autowired
    AccBalanceRepo accBalanceRepo;
    @Autowired
    CusAccMapRepo cusAccMapRepo;
    @Autowired
    TransactionRepo transactionRepo;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEntity<TransactionResponse> createTransactionCredit(NewTransaction newTransaction) {
        System.out.println("in creating transaction function");
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        UUID acc_id = newTransaction.getAcc_id();
        UUID to_acc_id = newTransaction.getTo_acc_id();
        double amount = newTransaction.getAmount();

        UUID tid1 = UUID.randomUUID();
        UUID tid2 = UUID.randomUUID();
        UUID rid = UUID.randomUUID();


        Optional<AccBalance> senderOptional = accBalanceRepo.findById(acc_id);
        System.out.println(senderOptional);
        if (senderOptional.isPresent()) {
            AccBalance sender = senderOptional.get();

            double senderBalance = sender.getBalance();
            if (senderBalance >= amount) {
                sender.setBalance(senderBalance - amount);
                AccTransaction at1 = new AccTransaction(tid1,rid,acc_id,0,amount,sender.getBalance(),currentTimestamp);
                transactionRepo.save(at1);
                accBalanceRepo.save(sender);
            } else {
                return ResponseEntity.badRequest().body(new TransactionResponse("Insufficient balance in sender's account","hct400",UUID.fromString("00000000-0000-0000-0000-000000000000")));

            }
        } else {
            return ResponseEntity.badRequest().body(new TransactionResponse("Sender account not found","hct400",UUID.fromString("00000000-0000-0000-0000-000000000000")));
        }

        Optional<AccBalance> receiverOptional = accBalanceRepo.findById(to_acc_id);
        if (receiverOptional.isPresent()) {
            AccBalance receiver = receiverOptional.get();
            double receiverBalance = receiver.getBalance();
            receiver.setBalance(receiverBalance + amount);
            AccTransaction at2 = new AccTransaction(tid2,rid,to_acc_id,amount,0,receiver.getBalance(),currentTimestamp);
            transactionRepo.save(at2);
            accBalanceRepo.save(receiver);
        } else {
            return ResponseEntity.badRequest().body(new TransactionResponse("Receiver account not found","hct400",UUID.fromString("00000000-0000-0000-0000-000000000000")));
        }


        return ResponseEntity.ok(new TransactionResponse("Transaction Success","HCT200",rid));
    }


    public ResponseEntity<Object> getTransactionByAccId(UUID accid) {

        List<AccTransaction> li=transactionRepo.findByAccId(accid);
        if(li.isEmpty()){
            Exceptionresponse er=new Exceptionresponse("cannot found","HCT404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }
        else {
            return ResponseEntity.ok(li);
        }

    }

    public ResponseEntity<Object> getTransactionByRefId(UUID refId) {

        List<AccTransaction> li=transactionRepo.findByRefId(refId);
        if(li.isEmpty()){
            Exceptionresponse er=new Exceptionresponse("cannot found","HCT404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }
        else {
            return ResponseEntity.ok(li);
        }
    }

    public ResponseEntity<Object> getTransactionByAccIdRefId(UUID l1, UUID l2) {

        Optional<AccTransaction> acc= Optional.ofNullable(transactionRepo.findByAccIdRefId(l1, l2));
        if(acc.isEmpty()){
            Exceptionresponse er=new Exceptionresponse("cannot found","HCT404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }
        else {
            return ResponseEntity.ok(acc);
        }

    }
}
