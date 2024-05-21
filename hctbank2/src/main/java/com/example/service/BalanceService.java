package com.example.service;

import com.example.model.AccBalance;
import com.example.model.CusAccMap;
import com.example.model.Response.BalanceResponse;
import com.example.model.Response.Exceptionresponse;
import com.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BalanceService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CustomerAddressRepo customerAddressRepo;
    @Autowired
    AccBalanceRepo accBalanceRepo;
    @Autowired
    CusAccMapRepo cusAccMapRepo;

    public ResponseEntity<Object> getBalanceByCustId(UUID id) {
        try{
            CusAccMap cap=cusAccMapRepo.findByCustId(id);
            Optional<AccBalance> ab=accBalanceRepo.findById(cap.getAccBalance().getAccId());
            if(ab.isPresent()){
                BalanceResponse br=new BalanceResponse(cap.getAccBalance().getAccId(), ab.get().getBalance());
                return ResponseEntity.ok(br);
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Exceptionresponse("account details not found","hct404"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Exceptionresponse("account details not found","hct404"));
        }

    }

    public ResponseEntity<Object> getBalanceByAccId(UUID acc_id) {
        try {
            Optional<AccBalance> ab=accBalanceRepo.findById(acc_id);
            if(ab.isPresent()){
                BalanceResponse br=new BalanceResponse(acc_id,ab.get().getBalance());
                return ResponseEntity.ok(br);
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Exceptionresponse("customer details not found","hct404"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Exceptionresponse("customer details not found","hct404"));
        }

    }
}
