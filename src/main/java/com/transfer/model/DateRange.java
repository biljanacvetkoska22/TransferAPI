package com.transfer.model;

import java.time.OffsetDateTime;

public class DateRange{
	
	OffsetDateTime dateFrom;
	OffsetDateTime dateTo;
	
	public DateRange() {}
	
	public DateRange(OffsetDateTime dateFrom, OffsetDateTime dateTo) {
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;					
	}
	
	public OffsetDateTime getDateFrom() {
		return dateFrom;
	}
	
	public OffsetDateTime getDateTo() {
		return dateTo;
	}
}
