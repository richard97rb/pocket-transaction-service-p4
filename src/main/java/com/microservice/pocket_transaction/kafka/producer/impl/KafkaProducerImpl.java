package com.microservice.pocket_transaction.kafka.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.microservice.pocket_transaction.kafka.producer.IKafkaProducer;

@Service
public class KafkaProducerImpl implements IKafkaProducer {

	@Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	public ListenableFuture<SendResult<String, Object>> sendJson(String topic, Object customer) {
		return kafkaTemplate.send(topic, customer);
	}

	
	
}
