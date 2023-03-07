package be.pxl.superhero.service.impl;

import be.pxl.superhero.api.MissionDTO;
import be.pxl.superhero.api.SuperheroDTO;
import be.pxl.superhero.api.SuperheroDetailDTO;
import be.pxl.superhero.api.SuperheroRequest;
import be.pxl.superhero.domain.Mission;
import be.pxl.superhero.domain.Superhero;
import be.pxl.superhero.exception.ResourceNotFoundException;
import be.pxl.superhero.repository.MissionRepository;
import be.pxl.superhero.repository.SuperheroRepository;
import be.pxl.superhero.service.SuperheroService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    private final SuperheroRepository superheroRepository;
    private final MissionRepository missionRepository;

    public SuperheroServiceImpl(SuperheroRepository superheroRepository,
                                MissionRepository missionRepository) {
        this.superheroRepository = superheroRepository;
        this.missionRepository = missionRepository;
    }

    public List<SuperheroDTO> findAllSuperheroes() {
        return superheroRepository.findAll()
                .stream().map(this::mapSuperhero)
                .toList();
    }

    public SuperheroDetailDTO findSuperheroById(Long superheroId) {
        return superheroRepository.findById(superheroId)
                .map(this::mapSuperheroDetail)
                .orElseThrow(() -> new ResourceNotFoundException("Superhero", "ID", superheroId));
    }

    public Long createSuperhero(SuperheroRequest superheroRequest) {
        Optional<Superhero> existingSuperhero = superheroRepository.findSuperheroBySuperheroName(superheroRequest.superheroName());
        existingSuperhero.ifPresent(s -> {
            throw new ValidationException("This superhero name is already taken.");
        });

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

    public SuperheroDetailDTO mapSuperheroDetail(Superhero superhero) {
        return new SuperheroDetailDTO(superhero.getId(), superhero.getFirstName(), superhero.getLastName(), superhero.getSuperheroName(), superhero.getMissions().stream().map(this::mapMission).toList());
    }

    private MissionDTO mapMission(Mission mission) {
        return new MissionDTO(mission.getId(), mission.getName(), mission.isCompleted());
    }

    @Transactional
    public void addSuperheroToMission(Long superheroId, Long missionId) {
        Superhero superhero = superheroRepository.findById(superheroId).orElseThrow(() -> new ResourceNotFoundException("Superhero", "ID", superheroId));
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new ResourceNotFoundException("Mission", "ID", missionId));
        superhero.addMission(mission);
    }
}