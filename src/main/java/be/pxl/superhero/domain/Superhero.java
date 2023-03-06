package be.pxl.superhero.domain;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="superheroes")
public class Superhero {

	private static final Logger LOGGER = LogManager.getLogger(Superhero.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String superheroName;
	@Column(name="notes")
	private String description;
	@ManyToMany
	private List<Mission> missions = new ArrayList<>();

	public Superhero() {
		// JPA only
	}

	public Superhero(String firstName, String lastName, String superheroName) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating a new superhero...");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.superheroName = superheroName;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(String.format("FirstName of %s was revealed.", superheroName));
		}
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		if (LOGGER.isFatalEnabled()) {
			LOGGER.fatal(String.format("LastName of %s was revealed.", superheroName));
		}
		return lastName;
	}

	public void addMission(Mission mission) {
		if (mission.isCompleted()) {
			throw new IllegalArgumentException("This mission was already completed.");
		}
		if (onMission(mission)) {
			throw new IllegalArgumentException("This mission was already assigned.");
		}
		missions.add(mission);
		mission.addSuperhero(this);
	}

	public boolean onMission(Mission mission) {
		return missions.stream().anyMatch(m -> m.getName().equals(mission.getName()));
	}

	public List<Mission> getMissions() {
		return missions;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuperheroName() {
		return superheroName;
	}

	public void setSuperheroName(String superheroName) {
		this.superheroName = superheroName;
	}

	@Override
	public String toString() {
		return superheroName;
	}
}