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
public class ResponsePocketTransactionDto {
	
	private String _id;

    private Double amount;
    
    private String transactionType;

    private String pocketNumber;
    
    private String description;
    
    private LocalDateTime transactionDate;
}
