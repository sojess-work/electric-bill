package com.kseb.electricbillingservice.repository;

import com.kseb.electricbillingservice.entity.CustomerBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBillRepository extends JpaRepository<CustomerBill,Long> {
}
