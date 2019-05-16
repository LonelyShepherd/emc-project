package mk.finki.ukim.emt.lab.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    public int id;
    public String name;
}
