package be.pxl.superhero.api;

import be.pxl.superhero.service.SuperheroService;
import be.pxl.superhero.service.impl.SuperheroIdCardGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/superheroes")
public class SuperheroController {

	private final SuperheroService superheroService;
	private final SuperheroIdCardGenerator superheroIdCardGenerator;

	public SuperheroController(SuperheroService superheroService,
							   SuperheroIdCardGenerator superheroIdCardGenerator) {
		this.superheroService = superheroService;
		this.superheroIdCardGenerator = superheroIdCardGenerator;
	}

	@GetMapping
	public List<SuperheroDTO> getSuperheroes() {
		return superheroService.findAllSuperheroes();
	}

	@GetMapping("/{superheroId}")
	public SuperheroDTO2 getSuperheroById(@PathVariable Long superheroId) {
		SuperheroDTO superheroById = superheroService.findSuperheroById(superheroId);
		return new SuperheroDTO2(superheroById.id(), superheroById.firstName(), superheroById.lastName(), superheroById.superheroName());
	}
	
	@PostMapping
	public ResponseEntity<Long> createSuperhero(@RequestBody  SuperheroRequest superheroRequest) {
		return new ResponseEntity<>(superheroService.createSuperhero(superheroRequest), HttpStatus.CREATED);
	}
	
	@PutMapping("/{superheroId}")
	public SuperheroDTO updateSuperhero(@PathVariable Long superheroId, @RequestBody SuperheroRequest superheroRequest) {
		return superheroService.updateSuperhero(superheroId, superheroRequest);
	}
	
	@DeleteMapping("/{superheroId}")
	public ResponseEntity<Void> deleteSuperhero(@PathVariable Long superheroId) {
		boolean deleted = superheroService.deleteSuperhero(superheroId);
		return deleted? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/{superheroId}/idcard", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] requestSuperheroIdCard(@PathVariable Long superheroId) {

		SuperheroDTO superhero = superheroService.findSuperheroById(superheroId);
		ByteArrayInputStream bis = superheroIdCardGenerator.superheroIdCard(superhero);

		return bis.readAllBytes();
	}
}