package com.super_market.repository;

import com.super_market.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findByCashierEmail(String cashierEmail);
}
