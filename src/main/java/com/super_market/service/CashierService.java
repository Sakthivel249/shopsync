package com.super_market.service;

import com.super_market.model.Receipt;
import com.super_market.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashierService {

    @Autowired
    private ReceiptRepository receiptRepository;

    //Receipt Management

    public Receipt createReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public void deleteReceipt(Long id) {
        receiptRepository.deleteById(id);
    }

    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    public Optional<Receipt> getReceiptById(Long id) {
        return receiptRepository.findById(id);
    }

    public List<Receipt> getReceiptsByCashier(String cashierEmail) {
        return receiptRepository.findByCashierEmail(cashierEmail);
    }

    public List<Receipt> getReceiptsForLoggedUser(String email) {
        return receiptRepository.findByCashierEmail(email);
    }
}
