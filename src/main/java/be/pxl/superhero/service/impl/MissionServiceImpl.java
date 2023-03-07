package be.pxl.superhero.service.impl;

import be.pxl.superhero.api.MissionDTO;
import be.pxl.superhero.api.MissionDetailDTO;
import be.pxl.superhero.api.MissionRequest;
import be.pxl.superhero.api.SuperheroDTO;
import be.pxl.superhero.domain.Mission;
import be.pxl.superhero.domain.Superhero;
import be.pxl.superhero.exception.ResourceNotFoundException;
import be.pxl.superhero.repository.MissionRepository;
import be.pxl.superhero.service.MissionService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MissionServiceImpl implements MissionService {

	private final MissionRepository missionRepository;

	@Autowired
	public MissionServiceImpl(MissionRepository missionRepository) {
		this.missionRepository = missionRepository;
	}

	@Override
	public List<MissionDTO> findAllMissions() {
		return missionRepository.findAll().stream().map(this::mapToDto).toList();
	}

	@Override
	public MissionDetailDTO findMissionById(Long missionId) {
		return missionRepository.findById(missionId).map(this::mapToMissionDetail).orElseThrow(() -> new ResourceNotFoundException("Mission", "ID", missionId));
	}

	@Override
	public MissionDTO createMission(MissionRequest missionRequest) {
		Optional<Mission> missionByName = missionRepository.findMissionByName(missionRequest.missionName());
		if (missionByName.isPresent()) {
			throw new ValidationException("Name already exists");
		}
		Mission mission = new Mission();
		mission.setName(missionRequest.missionName());
		Mission savedMission = missionRepository.save(mission);
		return mapToDto(savedMission);
	}



	@Transactional
	public void updateMission(Long missionId, MissionRequest missionRequest) {
		Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new ResourceNotFoundException("Mission", "ID", missionId));
		Optional<Mission> missionByName = missionRepository.findMissionByName(missionRequest.missionName());
		if (missionByName.isPresent() && !Objects.equals(missionByName.get().getId(), missionId)) {
			throw new ValidationException("Name already exists");
		}
		mission.setName(missionRequest.missionName());
		mission.setCompleted(missionRequest.completed());
	}

	@Override
	public void deleteMission(Long missionId) {
		Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new ResourceNotFoundException("Mission", "ID", missionId));
		missionRepository.delete(mission);
	}

	private MissionDTO mapToDto(Mission mission) {
		return new MissionDTO(mission.getId(), mission.getName(), mission.isCompleted());
	}

	private MissionDetailDTO mapToMissionDetail(Mission mission) {
		return new MissionDetailDTO(mission.getId(), mission.getName(), mission.isCompleted(), mission.getSuperheroes().stream().map(this::mapToSuperhero).toList());
	}

	private SuperheroDTO mapToSuperhero(Superhero superhero) {
		return new SuperheroDTO(superhero.getId(), superhero.getFirstName(), superhero.getLastName(), superhero.getSuperheroName());
	}
}