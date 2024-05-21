package com.example.service;

import com.example.model.*;
import com.example.model.Response.CustomerDetailsResponse;
import com.example.model.Response.CustomerResponse;
import com.example.model.Response.Exceptionresponse;
import com.example.repo.AccBalanceRepo;
import com.example.repo.AddressRepo;
import com.example.repo.CusAccMapRepo;
import com.example.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    AccBalanceRepo accBalanceRepo;
    @Autowired
    CusAccMapRepo cusAccMapRepo;

    public CustomerResponse addCustomer(CustomerDetailedAddress cdo) {
        UUID cid=UUID.randomUUID();
        UUID aid=UUID.randomUUID();
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        CustAddress ca=new CustAddress(aid,cdo.getCountry(),cdo.getCity(), cdo.getAddresslane(), cdo.getPin(),currentTimestamp);
        CustDetails cd=new CustDetails(cid,cdo.getName(), cdo.getPhone(),cdo.getEmail(),currentTimestamp,currentTimestamp);
        CustAddress caa = addressRepo.save(ca);
        cd.setAddress(caa);
        UUID acc_id=UUID.randomUUID();
        AccBalance ab=new AccBalance();
        ab.setAccId(acc_id);
        ab.setBalance(500.00);
        CustDetails cdd = customerRepo.save(cd);
        AccBalance acd = accBalanceRepo.save(ab);

        CusAccMap cusAccMap=new CusAccMap();
        cusAccMap.setAccBalance(acd);
        cusAccMap.setCustDetails(cdd);

        cusAccMapRepo.save(cusAccMap);

        return new CustomerResponse(cdd.getName(),cdd.getCust_id(),ab.getAccId(),500);
    }


    public ResponseEntity<Object> getCustomerById(UUID id) {
        try{
            Optional<CustDetails> cdget=customerRepo.findById(id);
            CustDetails cd= cdget.get();
            CusAccMap cam=cusAccMapRepo.findByCustId(cd.getCust_id());
            CustomerDetailsResponse cdr=new CustomerDetailsResponse(cd.getName(),cd.getPhone(),cd.getEmail(),cd.getCust_id(), cam.getAccBalance().getAccId(), cd.getCreated());
            return ResponseEntity.ok(cdr);
        }
        catch (Exception e){
            Exceptionresponse er=new Exceptionresponse("customer details not found","hct404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }

    }

    public ResponseEntity<Object> getCustomers() {

        try{
            List<CustDetails> list= customerRepo.findAll();
            List<CustomerDetailsResponse> cdrlist=new ArrayList<>();
            for(CustDetails cust:list){
                CusAccMap cam=cusAccMapRepo.findByCustId(cust.getCust_id());
                CustomerDetailsResponse cdr=new CustomerDetailsResponse(cust.getName(),cust.getPhone(),cust.getEmail(),cust.getCust_id(), cam.getAccBalance().getAccId(), cust.getCreated());
                cdrlist.add(cdr);
            }
            return ResponseEntity.ok(cdrlist);
        }
        catch (Exception e){
            Exceptionresponse er=new Exceptionresponse(e.getMessage(), "Hct404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
        }

    }
}
