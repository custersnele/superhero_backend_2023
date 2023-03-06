package be.pxl.superhero;

import be.pxl.superhero.domain.Superhero;
import be.pxl.superhero.repository.SuperheroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final SuperheroRepository superheroRepository;

    public StartupRunner(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        superheroRepository.save(new Superhero("Clark", "Kent", "Superman"));
        superheroRepository.save(new Superhero("Bruce", "Wayne", "Batman"));
    }
}
