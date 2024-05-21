package com.example.repo;

import com.example.model.AccTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<AccTransaction, UUID> {

    @Query(value = "select * from AccTransaction at where at.acc_id=:acc_id ",nativeQuery = true)
    List<AccTransaction> findByAccId(UUID acc_id);

    @Query(value = "select * from AccTransaction bt where bt.trans_refid=:ref_id ",nativeQuery = true)
    List<AccTransaction> findByRefId(UUID ref_id);

    @Query(value = "select * from AccTransaction bt where bt.trans_refid=:l2 and bt.acc_id=:l1 ",nativeQuery = true)
    AccTransaction findByAccIdRefId(UUID l1, UUID l2);
}
