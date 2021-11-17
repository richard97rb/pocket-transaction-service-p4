package com.microservice.pocket_transaction.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservice.pocket_transaction.entities.PocketTransaction;

public interface IPocketTransactionRepository extends MongoRepository<PocketTransaction, ObjectId>{

	public List<PocketTransaction> findByPocketNumber(String pocketNumber);
	
}
