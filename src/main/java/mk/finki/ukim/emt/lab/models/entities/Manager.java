package mk.finki.ukim.emt.lab.models.entities;

import mk.finki.ukim.emt.lab.consts.Role;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "managers")
public class Manager extends User {
    public Manager() {
        super();
        super.role = Role.Manager;
    }

    @OneToOne
    @JoinColumn(name = "department_id")
    public Department department;
}
