package mk.finki.ukim.emt.lab.models.entities;

import mk.finki.ukim.emt.lab.consts.Role;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee extends User {
    public Employee() {
        super();
        super.role = Role.Employee;
    }
}
