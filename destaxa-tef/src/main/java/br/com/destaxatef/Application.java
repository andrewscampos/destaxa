package br.com.destaxatef;

import org.jpos.util.NameRegistrar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.destaxatef.infra.config.SpringContextHolder;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		NameRegistrar.register("spring", SpringContextHolder.getApplicationContext());
	}
}
