package br.com.destaxatef.infra.provider;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.q2.Q2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import jakarta.annotation.PostConstruct;

@Configuration
public class AutorizadorProvider {

	@Autowired
	private ResourceLoader resourceLoader;

	@PostConstruct
	void init() throws IOException, ISOException {
		Resource resource = resourceLoader.getResource("classpath:");
		Q2 q2 = new Q2(resource.getFile().getAbsolutePath());
		q2.start();

	}
}
