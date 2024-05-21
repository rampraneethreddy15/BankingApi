package com.example.repo;

import com.example.model.CusAccMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CusAccMapRepo extends JpaRepository<CusAccMap,Long> {
    @Query(value = "select * from CusAccMap where cust_details_cust_id=:id " ,nativeQuery = true)
    CusAccMap findByCustId(UUID id);

}
