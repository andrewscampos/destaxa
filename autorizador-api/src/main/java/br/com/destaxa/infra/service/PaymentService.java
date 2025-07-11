package br.com.destaxa.infra.service;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.MUX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.destaxa.infra.dto.request.AuthorizationRequest;
import br.com.destaxa.infra.dto.response.AuthorizationResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

	@Autowired
	private MUX mux;
	
	@Autowired
	private ISOMsgFactory isoMsgFactory;
	
	@Autowired
	private AutorizarionResponseFactory authorizationResponseFactory;

	public AuthorizationResponse processPayment(String identifier, AuthorizationRequest request) {
		try {
			
			ISOMsg networkTestMessage = isoMsgFactory.createAuthorizationRequest(request);
			
			ISOMsg responseIsoMsg = mux.request(networkTestMessage, 3000);
			log.info("Conectado ao servidor Q2");
			
			if (responseIsoMsg == null) {
			    log.warn("Timeout ao aguardar resposta do MUX para STAN={}", networkTestMessage.getString(11));
			} else {
			    log.info("Resposta recebida MTI={} CódResp={}", responseIsoMsg.getMTI(), responseIsoMsg.getString(39));
			}
			if(responseIsoMsg == null) {
				log.error("Resposta nula do servidor Q2, retornando erro de timeout");
				return authorizationResponseFactory.errorNull(request);
			}
			return authorizationResponseFactory.create(responseIsoMsg, request.getValue());

		} catch (Exception e) {
			return authorizationResponseFactory.errorNull(request);
		}
		
	}




	
	 
}