package be.pxl.superhero.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Mission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private boolean completed;

	@ManyToMany(mappedBy = "missions")
	private List<Superhero> superheroes = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void addSuperhero(Superhero superhero) {
		superheroes.add(superhero);
	}

	public List<Superhero> getSuperheroes() {
		return superheroes;
	}


}