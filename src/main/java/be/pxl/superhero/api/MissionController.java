package be.pxl.superhero.api;

import be.pxl.superhero.service.MissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missions")
public final class MissionController {

	private final MissionService missionService;

	@Autowired
	public MissionController(MissionService missionService) {
		this.missionService = missionService;
	}

	@GetMapping
	public List<MissionDTO> getMissions() {
		return missionService.findAllMissions();
	}

	@GetMapping("/{missionId}")
	public MissionDTO getMissionById(@PathVariable Long missionId) {
		return missionService.findMissionById(missionId);
	}
	
	@PostMapping
	public ResponseEntity<MissionDTO> createMission(@RequestBody MissionRequest missionRequest) {
		return new ResponseEntity<>(missionService.createMission(missionRequest), HttpStatus.CREATED);
	}
	
	@PutMapping("/{missionId}")
	public void updateMission(@PathVariable Long missionId, @Valid @RequestBody MissionRequest missionRequest) {
		 missionService.updateMission(missionId, missionRequest);
	}
	
	@DeleteMapping("/{missionId}")
	public void deleteMission(@PathVariable Long missionId) {
		missionService.deleteMission(missionId);
	}
}