package com.api.api.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.api.exception.ResourceNotFoundException;
import com.api.api.model.Transaction;
import com.api.api.repository.TransactionRepository;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
 
    @Autowired
    private TransactionRepository TransactionRepository;

    @GetMapping("/Transactions")
    public List<Transaction> getAllTransactions() {
        return TransactionRepository.findAll();
    }

    @GetMapping("/Transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(
    @PathVariable(value = "id") Long TransactionId) throws ResourceNotFoundException {
        Transaction Transaction = TransactionRepository.findById(TransactionId)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction not found on :: "+ TransactionId));
        return ResponseEntity.ok().body(Transaction);
    }

    @PostMapping("/Transactions")
    public Transaction createTransaction(@Valid @RequestBody Transaction Transaction) {
        return TransactionRepository.save(Transaction);
    }

    @PutMapping("/Transactions/{id}")
    public ResponseEntity<Transaction> updateTransaction(
    @PathVariable(value = "id") Long TransactionId,
    @Valid @RequestBody Transaction TransactionDetails) throws ResourceNotFoundException {
         Transaction Transaction = TransactionRepository.findById(TransactionId)
          .orElseThrow(() -> new ResourceNotFoundException("Transaction not found on :: "+ TransactionId));
  
        Transaction.setAmount(TransactionDetails.getAmount());
        Transaction.setAccountId(TransactionDetails.getAccountId());
        Transaction.setRemarks(TransactionDetails.getRemarks());
        Transaction.setUpdatedAt(new Date());
        final Transaction updatedTransaction = TransactionRepository.save(Transaction);
        return ResponseEntity.ok(updatedTransaction);
   }

   @DeleteMapping("/Transaction/{id}")
   public Map<String, Boolean> deleteTransaction(
       @PathVariable(value = "id") Long TransactionId) throws Exception {
       Transaction Transaction = TransactionRepository.findById(TransactionId)
          .orElseThrow(() -> new ResourceNotFoundException("Transaction not found on :: "+ TransactionId));

       TransactionRepository.delete(Transaction);
       Map<String, Boolean> response = new HashMap<>();
       response.put("deleted", Boolean.TRUE);
       return response;
   }
}