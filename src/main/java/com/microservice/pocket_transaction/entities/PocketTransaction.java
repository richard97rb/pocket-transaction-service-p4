package com.microservice.pocket_transaction.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "pocket_transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PocketTransaction {

	@Id
    ObjectId _id;

    Double amount;
    
    @Field("transaction_type")
    String transactionType;
    
    @Field("pocket_number")
    String pocketNumber;
    
    @Field("pocket_number_other")
    String pocketNumberOther;
    
    String description;
    
    @Field("transaction_date")
    LocalDateTime transactionDate;
	
}
