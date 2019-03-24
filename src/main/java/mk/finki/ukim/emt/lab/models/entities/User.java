package mk.finki.ukim.emt.lab.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public boolean activated;
}
