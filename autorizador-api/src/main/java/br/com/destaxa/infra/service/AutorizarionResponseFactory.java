package br.com.destaxa.infra.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.jpos.iso.ISOMsg;
import org.springframework.stereotype.Component;

import br.com.destaxa.infra.dto.request.AuthorizationRequest;
import br.com.destaxa.infra.dto.response.AuthorizationResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AutorizarionResponseFactory {

	
	public AuthorizationResponse create(ISOMsg isoResponse, BigDecimal value) {
	    AuthorizationResponse response = new AuthorizationResponse();

	    try {
	        if (isoResponse.hasField(127)) {
	            response.setPaymentId(isoResponse.getString(127));
	        }

	        response.setValue(value);

	        if (isoResponse.hasField(39)) {
	            response.setResponseCode(isoResponse.getString(39));
	        }

	        if ("00".equals(isoResponse.getString(39)) && isoResponse.hasField(38)) {
	        	String authorizationCode = String.format("%06d", isoResponse.getString(38));
	            response.setAuthorizationCode(authorizationCode);
	        }
	        if (isoResponse.hasField(13)) {
	            String mmdd = isoResponse.getString(13);
	            String year = String.valueOf(LocalDateTime.now().getYear());
	            String transactionDate = year + "-" + mmdd.substring(0, 2) + "-" + mmdd.substring(2, 4);
	            response.setTransactionDate(transactionDate);
	        }

	        // Campo 12 - Hora local da transação (HHmmss → converter para HH:mm:ss)
	        if (isoResponse.hasField(12)) {
	            String hhmmss = isoResponse.getString(12);
	            String formattedHour = hhmmss.substring(0, 2) + ":" + hhmmss.substring(2, 4) + ":" + hhmmss.substring(4, 6);
	            response.setTransactionHour(formattedHour);
	        }
	    } catch (Exception e) {
	        log.error("Erro ao mapear ISO para AuthorizationResponse", e);
	    }
	    return response;
	}
	
	public AuthorizationResponse errorNull(AuthorizationRequest request) {
		
		AuthorizationResponse errorResponse = new AuthorizationResponse();
		errorResponse.setResponseCode("96");
		errorResponse.setValue(request.getValue());
		return errorResponse;
	 
	}
}
