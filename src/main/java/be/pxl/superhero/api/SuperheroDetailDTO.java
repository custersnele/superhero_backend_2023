package be.pxl.superhero.api;

import java.util.List;

public record SuperheroDetailDTO(Long id, String firstName, String lastName, String superheroName, List<MissionDTO> missions) {
}
