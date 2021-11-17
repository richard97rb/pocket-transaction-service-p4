package com.microservice.pocket_transaction.entities.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PocketTransactionDto {

	private Double amount;

	private String transactionType;

	private String pocketNumber;

	private LocalDateTime transactionDate;

}
