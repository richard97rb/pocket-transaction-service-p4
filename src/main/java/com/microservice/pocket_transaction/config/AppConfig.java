package com.microservice.pocket_transaction.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

enum PocketTransactionTypeEnum {
//	DEPOSITO("DEPOSITO"), ENVIO("ENVIO"), PAGO("PAGO"); // RETIRO
	ENVIO_DINERO("ENVIO_DINERO"), RECIBE_DINERO("RECIBE_DINERO"), TRANSFERENCIA("TRANSFERENCIA");

	private String type;

	PocketTransactionTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}

@Component
public class AppConfig {
	
	public Optional<String> getTransactionTypeByName(String name){
		PocketTransactionTypeEnum[] transactionTypes = PocketTransactionTypeEnum.values();
        Optional<PocketTransactionTypeEnum> typeEnum = Arrays.stream(transactionTypes)
                .filter(x->x.getType().equals(name))
                .findFirst();
        return Optional.ofNullable(typeEnum.get().getType());
    }
	
}
