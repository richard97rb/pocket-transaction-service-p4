package com.microservice.pocket_transaction.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePocketTransactionDto {

	private Double amount;

    private String transactionType;

    private String pocketNumber;
	
}
