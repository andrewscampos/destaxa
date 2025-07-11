package br.com.destaxatef.listener;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("autorizadorListener")
public class AutorizadorListener implements ISORequestListener {

	@Override
	public boolean process(ISOSource source, ISOMsg request) {
		try {
			log.info("Processando requisição ISO: {}", request.pack());

			String valorStr = request.getString(4);
			BigDecimal valor = new BigDecimal(valorStr).movePointLeft(2);

			if (valor.compareTo(BigDecimal.valueOf(1000)) > 0) {
				log.warn("Valor acima de R$1000, simulando timeout: {}", valor);
				 try {
				        Thread.sleep(60000*3); // 3 minutos
				    } catch (InterruptedException ignored) {}
				return true;
			}

			ISOMsg response = (ISOMsg) request.clone();
			response.setMTI("0210");

			if (valor.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0
					&& valor.remainder(BigDecimal.valueOf(2)).compareTo(BigDecimal.ZERO) == 0) {
				response.set(39, "00");
				
				String autorizationCode = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
				response.set(38, autorizationCode);
				log.info("Transação aprovada. Valor par: {}", valor);
			} else {
				response.set(39, "51");
				log.info("Transação negada. Valor ímpar: {}", valor);
			}

			source.send(response);
			return true;

		} catch (Exception e) {
			log.error("Erro ao processar transação ISO", e);
			return false;
		}
	}
}
