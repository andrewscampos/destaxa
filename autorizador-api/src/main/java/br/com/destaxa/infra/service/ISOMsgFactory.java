package br.com.destaxa.infra.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.jpos.iso.ISOMsg;
import org.springframework.stereotype.Component;

import br.com.destaxa.infra.dto.request.AuthorizationRequest;

@Component
public class ISOMsgFactory {
	
	
    public ISOMsg createAuthorizationRequest(AuthorizationRequest request) throws  Exception {
    	
    	
        ISOMsg msg = new ISOMsg();
        msg.setMTI("0200");
        msg.set(2, request.getCardNumber());
        msg.set(3, getProcessingCode(request));
        msg.set(4, formatAmount(request.getValue()));
        msg.set(7, formatDate("MMddHHmmss"));
        msg.set(11, generateStan());
        msg.set(12, formatDate("HHmmss"));
        msg.set(13, formatDate("MMdd"));
        msg.set(14, formatExpirationDate(request));
        msg.set(22, "012");
        msg.set(42, generateMerchantId());
        msg.set(49, "986");
        msg.set(67, formatInstallments(request.getInstallments()));

        return msg;
    }

    private String getProcessingCode(AuthorizationRequest request) {
        return request.getInstallments() <= 1 ? "003000" : "003001";
    }

    private String formatAmount(BigDecimal value) {
        long cents = value.multiply(BigDecimal.valueOf(100)).longValue();
        return String.format("%012d", cents);
    }

    private String formatDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    private String generateStan() {
        return String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
    }

    private String formatExpirationDate(AuthorizationRequest request) {
        return String.format("%02d%02d", Integer.parseInt(request.getExpMonth()), request.getExpYear());
    }

    private String generateMerchantId() {
        return String.format("MERCHANT%07d", ThreadLocalRandom.current().nextInt(0, 10_000_000)).substring(0, 15);
    }

    private String formatInstallments(int installments) {
        return String.format("%02d", installments);
    }
}
