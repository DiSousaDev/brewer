package br.com.diego.brewer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class BrewerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BrewerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//BCryptPasswordEncoder encod = new BCryptPasswordEncoder();
		//System.out.println("admin = " + encod.encode("admin"));

	}
}
