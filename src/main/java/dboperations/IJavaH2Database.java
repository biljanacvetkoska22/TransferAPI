package dboperations;

import java.util.List;

import com.transfer.model.Transfer;

public interface IJavaH2Database {

	List<Transfer> getTransactionsByDate(String dateFrom, String dateTo);

	String getFrequentAccount();

	void AlterCreateTransfer();

}