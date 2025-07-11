package br.com.destaxa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.destaxa.infra.dto.request.AuthorizationRequest;
import br.com.destaxa.infra.dto.response.AuthorizationResponse;
import br.com.destaxa.infra.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/authorization")
@Slf4j
public class AuthorizationController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<AuthorizationResponse> authorize(@RequestHeader("x-identifier") String identifier,
			@Valid @RequestBody AuthorizationRequest request) {

		log.info("Recebida solicitação de autorização - Identificador: {}, External ID: {}", identifier,
				request.getExternalId());

		try {
			AuthorizationResponse response = paymentService.processPayment(identifier, request);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Erro ao processar autorização", e);
			return ResponseEntity.internalServerError().build();
		}
	}
}