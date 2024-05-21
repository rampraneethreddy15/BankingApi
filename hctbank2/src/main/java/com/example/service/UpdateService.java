package com.example.service;

import com.example.model.CustDetails;
import com.example.model.CustomerDetailedAddress;
import com.example.repo.CustomerAddressRepo;
import com.example.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CustomerAddressRepo customerAddressRepo;


    public String updateCustomerDetails(UUID id, CustomerDetailedAddress cdo) {

        CustDetails cd=customerRepo.findById(id).get();
        cd.setPhone(cdo.getPhone());
        cd.setName(cdo.getName());
        cd.setEmail(cdo.getEmail());
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        cd.setUpdated(currentTimestamp);
        customerRepo.save(cd);
        return "updated";
    }
}
