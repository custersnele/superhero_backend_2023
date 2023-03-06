package be.pxl.superhero.service;

import be.pxl.superhero.api.MissionDTO;
import be.pxl.superhero.api.MissionRequest;

import java.util.List;

public interface MissionService {

	List<MissionDTO> findAllMissions();

	MissionDTO findMissionById(Long missionId);

	MissionDTO createMission(MissionRequest missionRequest);

	void updateMission(Long missionId, MissionRequest missionRequest);

	void deleteMission(Long missionId);
}