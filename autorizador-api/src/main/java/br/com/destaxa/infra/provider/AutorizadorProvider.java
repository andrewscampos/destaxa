package br.com.destaxa.infra.provider;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.MUX;
import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutorizadorProvider {
	 

	@Bean
	Q2 init() throws IOException, ISOException {
		Q2 q2 = new Q2();
		q2.start();
		return q2;

	}

	@Bean
	MUX mux(Q2 q2) throws ISOException, Exception {
		while (!q2.ready()) {
			ISOUtil.sleep(10);
		}
		return QMUX.getMUX("dx-mux");
	}
}
