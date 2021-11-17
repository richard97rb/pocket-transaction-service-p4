package com.microservice.pocket_transaction.kafka.producer;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public interface IKafkaProducer {
	
	ListenableFuture<SendResult<String, Object>> sendJson(String topic, Object customer);
	
}
