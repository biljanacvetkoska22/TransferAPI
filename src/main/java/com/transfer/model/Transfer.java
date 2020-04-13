package com.transfer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transfer {	
	
	@Id
	@GeneratedValue 
	@Column
	Integer number;
	@Column
	String sourceId;
	@Column
	String destinationId;
	@Column
	Integer amount;
	@Column
	Date transferedAt;
	
	public Transfer() { }

	public Transfer(String sourceId, String destinationId, Integer amount, Date transferedAt) {
		this.sourceId = sourceId;
		this.destinationId = destinationId;	
		this.amount = amount;
		this.transferedAt = transferedAt;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinaationId(String destinationId) {
		this.destinationId = destinationId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Date getTransferTime() {
		return transferedAt;
	}

	public void setTransferTime(Date transferedAt) {
		this.transferedAt = transferedAt;
	}
	

}
