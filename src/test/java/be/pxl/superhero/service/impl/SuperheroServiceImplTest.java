package be.pxl.superhero.service.impl;

import be.pxl.superhero.api.SuperheroDTO;
import be.pxl.superhero.api.SuperheroRequest;
import be.pxl.superhero.builder.SuperheroBuilder;
import be.pxl.superhero.domain.Superhero;
import be.pxl.superhero.repository.SuperheroRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuperheroServiceImplTest {

	private static final String SUPERHERO_NAME = "Superman";
	private static final String FIRST_NAME = "Clark";
	private static final String LAST_NAME = "Kent";
	private static final long SUPERHERO_ID = 115;

	@Mock
	private SuperheroRepository superheroRepository;
	@Mock
	private Superhero newSuperhero;

	@Captor
	private ArgumentCaptor<Superhero> superheroCaptor;

	@InjectMocks
	private SuperheroServiceImpl superheroService;

	private final Superhero superhero = SuperheroBuilder.aSuperhero()
			.withFirstName(FIRST_NAME)
			.withLastName(LAST_NAME)
			.withSuperheroName(SUPERHERO_NAME)
			.build();

	@Test
	public void throwsValidationExceptionWhenSuperheroNameIsNotUnique() {
		when(superheroRepository.findSuperheroBySuperheroName(SUPERHERO_NAME)).thenReturn(Optional.of(superhero));
		SuperheroRequest request = new SuperheroRequest(FIRST_NAME, LAST_NAME, SUPERHERO_NAME);
		ValidationException validationException = assertThrows(ValidationException.class, () -> superheroService.createSuperhero(request));
		assertEquals("This superhero name is already taken.", validationException.getMessage());
	}

	@Test
	public void savesSuperheroWhenSuperheroNameIsUnique() {
		when(superheroRepository.findSuperheroBySuperheroName(SUPERHERO_NAME)).thenReturn(Optional.empty());
		when(newSuperhero.getId()).thenReturn(SUPERHERO_ID);
		when(superheroRepository.save(Mockito.any(Superhero.class))).thenReturn(newSuperhero);
		SuperheroRequest request = new SuperheroRequest(FIRST_NAME, LAST_NAME, SUPERHERO_NAME);
		Long newId = superheroService.createSuperhero(request);
		assertEquals(SUPERHERO_ID, newId);
		Mockito.verify(superheroRepository).save(superheroCaptor.capture());
		Superhero savedSuperhero = superheroCaptor.getValue();
		assertEquals(FIRST_NAME, savedSuperhero.getFirstName());
		assertEquals(LAST_NAME, savedSuperhero.getLastName());
		assertEquals(SUPERHERO_NAME, savedSuperhero.getSuperheroName());
	}

	@Test
	public void findAllSuperheroes() {
		Superhero hero1 = SuperheroBuilder.aSuperhero().withSuperheroName("Batman").build();
		Superhero hero2 = SuperheroBuilder.aSuperhero().withSuperheroName("Superman").build();
		when(superheroRepository.findAll()).thenReturn(Arrays.asList(hero1, hero2));
		List<SuperheroDTO> allSuperheroes = superheroService.findAllSuperheroes();
		assertThat(allSuperheroes.stream().map(SuperheroDTO::superheroName).collect(Collectors.toList()))
				.hasSize(2)
				.containsExactly("Batman", "Superman");
	}

}