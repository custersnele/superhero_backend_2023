package be.pxl.superhero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperheroBackendApplication {

	public static void main(String[] args) {
		System.out.println("Running superhero-backend");
		SpringApplication.run(SuperheroBackendApplication.class, args);
	}

}
