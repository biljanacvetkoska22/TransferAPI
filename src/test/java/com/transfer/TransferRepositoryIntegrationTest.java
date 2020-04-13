package com.transfer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.transfer.model.Transfer;
import com.transfer.repository.ITransfersRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class TransferRepositoryIntegrationTest {
	
	@Autowired
	private ITransfersRepository transferRepository;
	
	@Test
	public void testFindAll() {
		List<Transfer> accounts = transferRepository.findAll();
		assertThat(accounts.size(), is(greaterThanOrEqualTo(0)));
	}
	
	
}