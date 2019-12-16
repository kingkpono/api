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
import com.api.api.model.Account;
import com.api.api.repository.AccountRepository;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
 
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(
    @PathVariable(value = "id") Long accountId) throws ResourceNotFoundException {
        Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+accountId));
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(
    @PathVariable(value = "id") Long accountId,
    @Valid @RequestBody Account accountDetails) throws ResourceNotFoundException {
         Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+accountId));
  
        account.setAccountNumber(accountDetails.getAccountNumber());
        account.setBalance(accountDetails.getBalance());
        account.setCustomerId(accountDetails.getCustomerId());
        account.setUpdatedAt(new Date());
        final Account updatedAccount = accountRepository.save(account);
        return ResponseEntity.ok(updatedAccount);
   }

   @DeleteMapping("/accounts/{id}")
   public Map<String, Boolean> deleteAccount(
       @PathVariable(value = "id") Long accountId) throws Exception {
       Account account = accountRepository.findById(accountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+accountId));

       accountRepository.delete(account);
       Map<String, Boolean> response = new HashMap<>();
       response.put("deleted", Boolean.TRUE);
       return response;
   }
}