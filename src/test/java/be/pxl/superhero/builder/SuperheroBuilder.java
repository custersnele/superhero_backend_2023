package be.pxl.superhero.builder;

import be.pxl.superhero.domain.Superhero;

public final class SuperheroBuilder {
    private String firstName;
    private String lastName;
    private String superheroName;
    private Long id;

    private SuperheroBuilder() {
    }

    public static SuperheroBuilder aSuperhero() {
        return new SuperheroBuilder();
    }

    public SuperheroBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SuperheroBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SuperheroBuilder withSuperheroName(String superheroName) {
        this.superheroName = superheroName;
        return this;
    }

    public Superhero build() {
        Superhero superhero = new Superhero();
        superhero.setFirstName(firstName);
        superhero.setLastName(lastName);
        superhero.setSuperheroName(superheroName);
        return superhero;
    }
}
