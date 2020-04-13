package com.transfer.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transfer.model.Account;
import com.transfer.model.Transfer;
import com.transfer.repository.IAccountsRepository;
import com.transfer.repository.ITransfersRepository;

import dboperations.IJavaH2Database;
import dboperations.JavaH2Database;

/*
Used to create transfers and update balances.
Interfaces are used to ensure we follow the SOLID principles 
*/

@RestController
@RequestMapping("api/v1")
public class TransferController {

	@Autowired
	private IAccountsRepository accountsRepository;

	@Autowired
	private ITransfersRepository transfersRepository;

	private IJavaH2Database h2Database;

	@RequestMapping(value = "transfers", method = RequestMethod.GET)
	public List<Transfer> list() {
		try{
			return transfersRepository.findAll();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/* TODO: NEED TO FIX THIS SO I CAN USE A TRANSACTION WRAPPER AND ROLLBACK ON FAIL*/
	@RequestMapping(value = "transfers", method = RequestMethod.POST)
	public Transfer create(@RequestBody Transfer transfer) {
		try{
			LocalDateTime dateNow = LocalDateTime.now();
			Date date = Date.from(dateNow.atZone(ZoneId.systemDefault()).toInstant());
			Account source = accountsRepository.findOne(transfer.getSourceId());
			Account destination = accountsRepository.findOne(transfer.getDestinationId());
			if (source != null && !ObjectUtils.isEmpty(source) && destination != null
					&& !ObjectUtils.isEmpty(destination)) {
				Integer transferAmount = transfer.getAmount();
				Integer newSourceAmount = source.getBalance() - transferAmount;
				Integer newDestinationAmount = destination.getBalance() + transferAmount;

				source.setBalance(newSourceAmount);
				destination.setBalance(newDestinationAmount);

				accountsRepository.saveAndFlush(source);
				accountsRepository.saveAndFlush(destination);

				transfer.setTransferTime(date);
				return transfersRepository.saveAndFlush(transfer);
			}
		}
		catch (Exception ex)
		{
		   System.out.println(ex.getMessage()); /* add dbConnection.rollback(); and finally close connection */
		}
		return null;
	}		
	

	@RequestMapping(value = "transfers/source/{sourceId}", method = RequestMethod.GET)
	public Transfer get(@PathVariable String sourceId) {
		try {
			return transfersRepository.findOne(sourceId);
		}
		catch (Exception ex)
		{
		   System.out.println(ex.getMessage());
		}
		return null;		
	}

	@RequestMapping(value = "transfers/source/frequent", method = RequestMethod.GET)
	public Account mostFrequentlyUsedAccount() {
		try {
			h2Database = new JavaH2Database();
			String accountId = h2Database.getFrequentAccount();
			Account account = accountsRepository.findOne(accountId);
			return account;
		}
		catch (Exception ex)
		{
		   System.out.println(ex.getMessage());
		}
		return null;	
	}

	@RequestMapping(value = "transfers/dateFrom/{from}/dateTo/{to}", method = RequestMethod.GET)
	public List<Transfer> transactionsWithinDateRange(@PathVariable String from, @PathVariable String to) {

		List<Transfer> result = new ArrayList<Transfer>();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFrom = formatter.parse(from);
			Date dateTo = formatter.parse(to);

			h2Database = new JavaH2Database();

			return h2Database.getTransactionsByDate(from, to);
		} catch (Exception ex) {
			System.out.println(ex.toString());			
		}

		return result;
	}
	
/*
Examples and usage: 
GET: http://localhost:8080/api/v1/accounts/highest
Gets the account with the highest funds. 

GET: http://localhost:8080/api/v1/transfers 
Gets all of the transfers.

POST: http://localhost:8080/api/v1/transfers
Creates a transaction from one account to another.

GET: http://localhost:8080/api/v1/transfers/dateFrom/2019-08-26/dateTo/2019-08-29
Gets all the transfers by a specific date range. 

GET: http://localhost:8080/api/v1/transfers/source/frequent
Gets the most frequently used account with its id and balance.
*/
}
