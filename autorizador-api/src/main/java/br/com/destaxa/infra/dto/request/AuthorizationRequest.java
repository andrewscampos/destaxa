package br.com.destaxa.infra.dto.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AuthorizationRequest {

	@NotBlank(message = "externalId é obrigatório")
	@JsonProperty("external_id")
	private String externalId;

	@NotNull(message = "value é obrigatório")
	@Positive(message = "value deve ser positivo")
	@JsonProperty("value")
	private BigDecimal value;

	@NotBlank(message = "cardNumber é obrigatório")
	@JsonProperty("card_number")
	private String cardNumber;

	@NotNull(message = "installments é obrigatório")
	@Min(value = 1, message = "installments deve ser >= 1")
	@JsonProperty("installments")
	private Integer installments = 1;

	@NotBlank(message = "CVV é obrigatório")
	@Pattern(regexp = "^\\d{3}$", message = "CVV deve conter exatamente 3 dígitos")
	@JsonProperty("cvv")
	private String cvv;

	@Pattern(regexp = "^(0?[1-9]|1[0-2])$", message = "expMonth deve estar entre 01 e 12")
	@NotBlank(message = "expMonth é obrigatório")
	@JsonProperty("exp_month")
	private String expMonth;

	@NotNull(message = "expYear é obrigatório")
	@Min(value = 0, message = "expYear deve estar entre 00 e 99")
	@Max(value = 99, message = "expYear deve estar entre 00 e 99")
	@JsonProperty("exp_year")
	private Integer expYear;

	@NotBlank(message = "holderName é obrigatório")
	@JsonProperty("holder_name")
	private String holderName;
}
