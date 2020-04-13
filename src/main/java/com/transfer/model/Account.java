package com.transfer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
	@Id
	@Column
	String id;
	@Column
	Integer balance;
	
	public Account() { }

	public Account(String id, Integer balance) {
		this.id = id;
		this.balance = balance;		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}
}

	