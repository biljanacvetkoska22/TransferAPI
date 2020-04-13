package com.transfer;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.transfer.controller.AccountController;
import com.transfer.model.Account;
import com.transfer.repository.IAccountsRepository;

public class AccountControllerTest {
	
	@InjectMocks
	private AccountController accountController;
	
	@Mock
	private IAccountsRepository accountRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAccountsGet() {
		Account account = new Account();
		account.setId("asfdf3");		
		when(accountRepository.findOne("asfdf3")).thenReturn(account);
		
		Account account1 = accountController.get("asfdf3");
		
		verify(accountRepository).findOne("asfdf3");
		
		assertEquals("asfdf3", account1.getId());
	}
}
