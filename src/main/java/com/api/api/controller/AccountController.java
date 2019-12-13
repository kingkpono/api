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
    private AccountRepository AccountRepository;

    @GetMapping("/Accounts")
    public List<Account> getAllAccounts() {
        return AccountRepository.findAll();
    }

    @GetMapping("/Accounts/{id}")
    public ResponseEntity<Account> getAccountById(
    @PathVariable(value = "id") Long AccountId) throws ResourceNotFoundException {
        Account Account = AccountRepository.findById(AccountId)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+ AccountId));
        return ResponseEntity.ok().body(Account);
    }

    @PostMapping("/Accounts")
    public Account createAccount(@Valid @RequestBody Account Account) {
        return AccountRepository.save(Account);
    }

    @PutMapping("/Accounts/{id}")
    public ResponseEntity<Account> updateAccount(
    @PathVariable(value = "id") Long AccountId,
    @Valid @RequestBody Account AccountDetails) throws ResourceNotFoundException {
         Account account = AccountRepository.findById(AccountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+ AccountId));
  
         account.setAccountNumber(AccountDetails.getAccountNumber());
         account.setBalance(AccountDetails.getBalance());
         account.setCustomerId(AccountDetails.getCustomerId());
        account.setUpdatedAt(new Date());
        final Account updatedAccount = AccountRepository.save(account);
        return ResponseEntity.ok(updatedAccount);
   }

   @DeleteMapping("/Account/{id}")
   public Map<String, Boolean> deleteAccount(
       @PathVariable(value = "id") Long AccountId) throws Exception {
       Account Account = AccountRepository.findById(AccountId)
          .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+ AccountId));

       AccountRepository.delete(Account);
       Map<String, Boolean> response = new HashMap<>();
       response.put("deleted", Boolean.TRUE);
       return response;
   }
}