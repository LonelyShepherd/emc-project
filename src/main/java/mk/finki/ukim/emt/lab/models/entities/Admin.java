package mk.finki.ukim.emt.lab.models.entities;

import mk.finki.ukim.emt.lab.consts.Role;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {
    public Admin() {
        super();
        super.role = Role.Admin;
    }
}
