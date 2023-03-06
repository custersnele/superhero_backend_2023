package be.pxl.superhero.service.impl;

import be.pxl.superhero.api.SuperheroDTO;
import be.pxl.superhero.api.SuperheroRequest;
import be.pxl.superhero.domain.Superhero;
import be.pxl.superhero.exception.ResourceNotFoundException;
import be.pxl.superhero.repository.SuperheroRepository;
import be.pxl.superhero.service.SuperheroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    private final SuperheroRepository superheroRepository;

    public SuperheroServiceImpl(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    public List<SuperheroDTO> findAllSuperheroes() {
        return superheroRepository.findAll()
                .stream().map(this::mapSuperhero)
                .toList();
    }

    public SuperheroDTO findSuperheroById(Long superheroId) {
        return superheroRepository.findById(superheroId)
                .map(this::mapSuperhero)
                .orElseThrow(() -> new ResourceNotFoundException("Superhero", "ID", superheroId));
    }

    public Long createSuperhero(SuperheroRequest superheroRequest) {
        Superhero superhero = new Superhero();
        superhero.setFirstName(superheroRequest.firstName());
        superhero.setLastName(superheroRequest.lastName());
        superhero.setSuperheroName(superheroRequest.superheroName());
        Superhero newSuperhero = superheroRepository.save(superhero);
        return newSuperhero.getId();
    }

    public SuperheroDTO updateSuperhero(Long superheroId, SuperheroRequest superheroRequest) {
        return superheroRepository.findById(superheroId).map(superhero -> {
            superhero.setFirstName(superheroRequest.firstName());
            superhero.setLastName(superheroRequest.lastName());
            superhero.setSuperheroName(superheroRequest.superheroName());
            return mapSuperhero(superheroRepository.save(superhero));
        }).orElseThrow(() -> new ResourceNotFoundException("Superhero", "id", superheroId));
    }

    public boolean deleteSuperhero(Long superheroId) {
        return superheroRepository.findById(superheroId)
                .map(superhero -> {
                    superheroRepository.delete(superhero);
                    return true;
                }).orElseThrow(() -> new ResourceNotFoundException("Superhero", "id", superheroId));

    }

    public SuperheroDTO mapSuperhero(Superhero superhero) {
        return new SuperheroDTO(superhero.getId(), superhero.getFirstName(), superhero.getLastName(), superhero.getSuperheroName());
    }
}