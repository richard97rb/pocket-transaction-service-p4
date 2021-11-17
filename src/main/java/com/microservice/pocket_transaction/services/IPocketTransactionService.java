package com.microservice.pocket_transaction.services;

import java.util.List;

import com.microservice.pocket_transaction.entities.dtos.CreatePocketTransactionDto;
import com.microservice.pocket_transaction.entities.dtos.CreatePocketTransferDto;
import com.microservice.pocket_transaction.entities.dtos.PocketTransactionDto;
import com.microservice.pocket_transaction.entities.dtos.ResponsePocketTransactionDto;

public interface IPocketTransactionService {

	ResponsePocketTransactionDto createTransaction(CreatePocketTransactionDto dto) throws Exception;
	ResponsePocketTransactionDto createTransfer(CreatePocketTransferDto dto) throws Exception;
	List<ResponsePocketTransactionDto> getTransactionsByPocketNumber(String pocketNumber) throws Exception;
}
