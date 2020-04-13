package com.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transfer.model.Transfer;

public interface ITransfersRepository extends JpaRepository<Transfer, String> {

}
