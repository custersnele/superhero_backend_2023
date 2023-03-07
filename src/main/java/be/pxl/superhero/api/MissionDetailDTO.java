package be.pxl.superhero.api;

import java.util.List;

public record MissionDetailDTO(Long id, String missionName, boolean completed, List<SuperheroDTO> superheroes) {
}
