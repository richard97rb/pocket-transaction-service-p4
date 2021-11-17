package com.microservice.pocket_transaction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.pocket_transaction.entities.dtos.CreatePocketTransactionDto;
import com.microservice.pocket_transaction.entities.dtos.CreatePocketTransferDto;
import com.microservice.pocket_transaction.entities.dtos.PocketTransactionDto;
import com.microservice.pocket_transaction.entities.dtos.ResponsePocketTransactionDto;
import com.microservice.pocket_transaction.services.IPocketTransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/api/pocket-transactions")
public class PocketTransactionController {

	@Autowired
	private IPocketTransactionService pocketTransactionService;

	@PostMapping("/")
	public Mono<ResponsePocketTransactionDto> createTransaction(@Validated @RequestBody CreatePocketTransferDto dto) throws Exception {
		return Mono.just(pocketTransactionService.createTransfer(dto));
	}
	
	@GetMapping("/pocket/{pocketNumber}")
    public Flux<ResponsePocketTransactionDto> getTransactionsByPocketId(@PathVariable String pocketNumber) throws Exception {
        return Flux.fromIterable(pocketTransactionService.getTransactionsByPocketNumber(pocketNumber));
    }

}
