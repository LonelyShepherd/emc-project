package mk.finki.ukim.emt.lab.models.entities;

import mk.finki.ukim.emt.lab.consts.Role;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "firstname")
    public String firstName;
    @Column(name = "lastname")
    public String lastName;
    @Column(name = "email")
    public String email;
    @Column(name = "role")
    public Role role;
    @ManyToOne
    @JoinColumn(name = "department_id")
    public Department department;
    @Column(name = "password")
    public String password;
    @Column(name = "activated")
    public boolean activated;
}
