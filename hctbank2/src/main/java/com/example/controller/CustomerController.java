package com.example.controller;


import com.example.model.*;
import com.example.model.Response.CustomerDetailsResponse;
import com.example.model.Response.CustomerResponse;
import com.example.model.Response.Exceptionresponse;
import com.example.model.Response.TransactionResponse;
import com.example.repo.AddressRepo;
import com.example.repo.CustomerRepo;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("hct")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    BalanceService balanceService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    UpdateService updateService;

    @GetMapping("customers")
    public ResponseEntity<Object> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable UUID id){
        return customerService.getCustomerById(id);
    }

    @PostMapping("customers")
    public CustomerResponse addCustomers(@RequestBody CustomerDetailedAddress cdo){

        return customerService.addCustomer(cdo);

    }
    @GetMapping("balances")
    public ResponseEntity<Object> getBalance(@RequestParam(required = false) String cust_id, @RequestParam(required = false) String acc_id){

        if( cust_id == null && acc_id ==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Exceptionresponse("invalid details","hct404"));
        }
        else if(cust_id !=null && acc_id==null){
            return balanceService.getBalanceByCustId(UUID.fromString(cust_id));
        }
        else {
            return balanceService.getBalanceByAccId(UUID.fromString(acc_id));
        }

    }

    @PostMapping("transactions")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody NewTransaction newTransaction){
        String type=newTransaction.getType();
        if(type.equals("credit"))
        {
            System.out.println("calling function");
            return transactionService.createTransactionCredit(newTransaction);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new TransactionResponse("invalid type","Hct404",UUID.fromString("00000000-0000-0000-0000-000000000000")));
    }
    @GetMapping("transactions")
    public ResponseEntity<Object> getTransactions(@RequestParam(required = false) String acc_id, @RequestParam(required = false) String ref_id){
        if( acc_id == null && ref_id == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        }
        else if(acc_id!=null && ref_id==null){
            System.out.println("using accid");
            return transactionService.getTransactionByAccId(UUID.fromString(acc_id));
        }
        else if (acc_id==null && ref_id!=null) {

            return transactionService.getTransactionByRefId(UUID.fromString(ref_id));
        }
        else{
            return transactionService.getTransactionByAccIdRefId(UUID.fromString(acc_id),UUID.fromString(ref_id));
        }


    }

    @PutMapping("updatedetails/{id}")
    public String updateCustomerDetails(@PathVariable UUID id,@RequestBody CustomerDetailedAddress cdo){
        return updateService.updateCustomerDetails(id,cdo);
    }


}
