package com.api.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.api.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	
	 
}