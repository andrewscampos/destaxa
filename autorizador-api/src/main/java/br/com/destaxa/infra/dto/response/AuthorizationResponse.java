package br.com.destaxa.infra.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthorizationResponse {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("authorization_code")
    private String authorizationCode;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("transaction_hour")
    private String transactionHour;
}
