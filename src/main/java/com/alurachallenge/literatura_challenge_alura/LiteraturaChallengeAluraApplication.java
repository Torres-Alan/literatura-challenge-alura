package com.alurachallenge.literatura_challenge_alura;

import com.alurachallenge.literatura_challenge_alura.principal.Principal;
import com.alurachallenge.literatura_challenge_alura.repository.AutorRepository;
import com.alurachallenge.literatura_challenge_alura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaChallengeAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;


	public static void main(String[] args) {
		SpringApplication.run(LiteraturaChallengeAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.mostrarMenu();
	}
}
