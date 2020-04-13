package com.transfer.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transfer.model.Account;
import com.transfer.repository.IAccountsRepository;

/*
 Used for CRUD accounts. Mainly to create accounts and their balances.
 The use of interfaces is applied to ensure we have loosely coupled classes.
 */

@RestController
@RequestMapping("api/v1")
public class AccountController {
	
	@Autowired
	private IAccountsRepository accountsRepository;
	
	@RequestMapping(value = "accounts", method = RequestMethod.GET)
	public List<Account> list(){
		return accountsRepository.findAll();
	}
	
	@RequestMapping(value="accounts", method = RequestMethod.POST)
	public Account create(@RequestBody Account account){
		try{
			if(accountsRepository.findOne(account.getId())!= null)
				throw new Exception("The account already exists");
			
			else 
				return accountsRepository.saveAndFlush(account);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "accounts/{id}", method = RequestMethod.GET)
	public Account get(@PathVariable String id) {
		try{
			return accountsRepository.findOne(id);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "accounts/{id}", method = RequestMethod.PUT)
	public Account update(@PathVariable String id, @RequestBody Account account) {
		try
		{
			Account existingAccount = accountsRepository.findOne(id);
			BeanUtils.copyProperties(account, existingAccount);
			return accountsRepository.saveAndFlush(account);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
		
	}
	
	@RequestMapping(value = "accounts/{id}", method = RequestMethod.DELETE)
	public Account delete(@PathVariable String id) {
		try
		{
			Account existingAccount = accountsRepository.findOne(id);
			accountsRepository.delete(existingAccount);
			return existingAccount;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "accounts/highest", method = RequestMethod.GET)
	public Account getHighest(){
		try
		{
			List<Account> accounts = accountsRepository.findAll();
			Account account = Collections.max(accounts, Comparator.comparing(s -> s.getBalance()));
			return account;	
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;	
	}
	
/*Examples and usage:
 * 
GET: http://localhost:8080/api/v1/accounts
Gets a list of all accounts and their balances 

POST: http://localhost:8080/api/v1/accounts
body: {"id":"asfdf9","balance":10000}
Creates an account with id asdff9 with initial balance 10000

PUT: http://localhost:8080/api/v1/accounts/asfdf9
body: {"id":"asfdf9","balance":3000} 
Is used to edit an account. But this is not really used or applicable in the situation.

GET: http://localhost:8080/api/v1/accounts/highest
Gets the account with the highest funds. 
 * */
}
