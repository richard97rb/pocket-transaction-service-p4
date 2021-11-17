package com.microservice.pocket_transaction.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.pocket_transaction.config.AppConfig;
import com.microservice.pocket_transaction.entities.PocketTransaction;
import com.microservice.pocket_transaction.entities.dtos.CreatePocketTransactionDto;
import com.microservice.pocket_transaction.entities.dtos.CreatePocketTransferDto;
import com.microservice.pocket_transaction.entities.dtos.PocketTransactionDto;
import com.microservice.pocket_transaction.entities.dtos.ResponsePocketTransactionDto;
import com.microservice.pocket_transaction.kafka.producer.IKafkaProducer;
import com.microservice.pocket_transaction.kafka.utils.PocketTransactionUtils;
import com.microservice.pocket_transaction.repositories.IPocketTransactionRepository;
import com.microservice.pocket_transaction.services.IPocketTransactionService;

@Service
public class PocketTransactionServiceImpl implements IPocketTransactionService {

	@Autowired
	private IPocketTransactionRepository pocketTransactionRepository;
	
	@Autowired
    private AppConfig appConfig; 
	
	@Autowired
	private IKafkaProducer kafkaProducer;
	
	public static final ModelMapper modelMapper = new ModelMapper();
	
	private void updateAmountHandler(Double amount, String transactionType, String pocketNumber){
        
		//Update amount
        PocketTransactionDto transactionDto = PocketTransactionDto.builder()
                .amount(amount) 
                .transactionType(transactionType)
                .pocketNumber(pocketNumber)
                .build();
        
        // Send to Update Account
        kafkaProducer.sendJson(PocketTransactionUtils.PRODUCER_TOPIC, transactionDto);
    }
	
	public ResponsePocketTransactionDto doTransaction(Double amount, String transactionType, String pocketNumber, 
			String pocketNumberOther) {
		
		String description = "";
		switch (transactionType) {
		case "ENVIO_DINERO":
				description =  "Enviados " + amount + " al monedero " + pocketNumberOther; 
			break;

		case "RECIBE_DINERO":
			description =  "Recibidos " + amount + " desde el monedero " + pocketNumberOther; 
			break;
		}
		
		PocketTransaction transaction = PocketTransaction.builder()
											.amount(amount)
											.pocketNumber(pocketNumber)
											.pocketNumberOther(pocketNumberOther)
											.transactionType(transactionType)
											.description(description)
											.transactionDate(LocalDateTime.now())
											.build();
		
		transaction = pocketTransactionRepository.save(transaction);
		
		//Update Amount (with kafka)
		updateAmountHandler(amount, transactionType, pocketNumber);//, pocketId);
		
		//Response
		ResponsePocketTransactionDto response = ResponsePocketTransactionDto.builder()
													._id(transaction.get_id().toString())
													.pocketNumber(pocketNumber)
													.description(transaction.getDescription())
													.amount(amount)
													.transactionType(transactionType)
													.transactionDate(transaction.getTransactionDate())
													.build();
		return response;
		
	}
	

	@Override
	public ResponsePocketTransactionDto createTransfer(CreatePocketTransferDto dto) throws Exception {
		
		String transactionType = appConfig.getTransactionTypeByName(dto.getTransactionType())
                .orElseThrow(()->new Exception("TRANSACTION_TYPE_NOT_FOUND"));
		
		ResponsePocketTransactionDto response = new ResponsePocketTransactionDto();
		switch (dto.getTransactionType()) {
			case "TRANSFERENCIA":
				//El qué envia el dinero
				response = doTransaction(dto.getAmount(), "ENVIO_DINERO", dto.getPocketNumberSource(), dto.getPocketNumberDestination());
				
				//El que recibe
				doTransaction(dto.getAmount(), "RECIBE_DINERO", dto.getPocketNumberDestination(), dto.getPocketNumberSource());
				break;
		}
		
		return response;
	}
	
	@Override
	public List<ResponsePocketTransactionDto> getTransactionsByPocketNumber(String pocketNumber) throws Exception {
		
		List<PocketTransaction> transactions = pocketTransactionRepository.findByPocketNumber(pocketNumber);
		List<ResponsePocketTransactionDto> response = new ArrayList<>();
		
		transactions.forEach(t -> {
			response.add(modelMapper.map(t, ResponsePocketTransactionDto.class));
		});
		
		return response;
	}
	
	//No usado
	@Override
	public ResponsePocketTransactionDto createTransaction(CreatePocketTransactionDto dto) throws Exception {
		
		String transactionType = appConfig.getTransactionTypeByName(dto.getTransactionType())
	                .orElseThrow(()->new Exception("POCKET_TRANSACTION_TYPE_NOT_FOUND"));
		
		//Create transaction
		ResponsePocketTransactionDto response = new ResponsePocketTransactionDto();
		switch (dto.getTransactionType()) {
		case "ENVIO_DINERO": //DEPOSITO
			response = doTransaction(dto.getAmount(), dto.getTransactionType(), dto.getPocketNumber(), null);
			break;

		case "RECIBE_DINERO": //RETIRO
			response = doTransaction(dto.getAmount(), dto.getTransactionType(), dto.getPocketNumber(), null);
			break;
		}
		
		return response;
	}

}
