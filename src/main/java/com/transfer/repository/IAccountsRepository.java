package com.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transfer.model.Account;

public interface IAccountsRepository extends JpaRepository<Account, String> {

}
