package be.pxl.superhero.api;

public class SuperheroDTO2 {

   private final Long id;
   private final String firstName;
   private final String lastName;
   private final String superheroName;

    public SuperheroDTO2(Long id, String firstName, String lastName, String superheroName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.superheroName = superheroName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSuperheroName() {
        return superheroName;
    }
}
