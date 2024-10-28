package com.booksmart.BookSmart;

import navegacionusuario.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Clase de inicio de nuestra aplicacion con Spring Boot implementando
// la interfaz CommandLineRunner.
@SpringBootApplication
public class BookSmartApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookSmartApplication.class, args);
	}

	// Implementacion y sobreescritura del metodo run en esta clase.
	@Override
	public void run(String... args) throws Exception {
		Menu iniciarAplicacion = new Menu();
		iniciarAplicacion.menuPrincipal();
	}
}
