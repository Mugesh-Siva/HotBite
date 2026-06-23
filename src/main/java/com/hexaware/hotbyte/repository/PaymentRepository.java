package com.hexaware.hotbyte.repository;

import com.hexaware.hotbyte.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByOrderOrderId(Integer orderId);
    Optional<Payment> findByTransactionId(String transactionId);
}
