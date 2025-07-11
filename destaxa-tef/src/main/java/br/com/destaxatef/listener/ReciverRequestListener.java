package br.com.destaxatef.listener;

import org.jpos.bsh.BSHRequestListener;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.util.NameRegistrar;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReciverRequestListener extends BSHRequestListener {

	private ISORequestListener delegate;

	@Override
	public boolean process(ISOSource source, ISOMsg m) {
		try {
			log.info("Requisição recebida pelo simulador: {}", m);
			if (delegate == null) {
				ApplicationContext ctx = (ApplicationContext) NameRegistrar.get("spring");
				delegate = (ISORequestListener) ctx.getBean("autorizadorListener");
			}
			return delegate.process(source, m);

		} catch (Exception e) {
			log.error("Erro ao processar requisição: {}", e.getMessage(), e);
		}
		return false;
	}
}