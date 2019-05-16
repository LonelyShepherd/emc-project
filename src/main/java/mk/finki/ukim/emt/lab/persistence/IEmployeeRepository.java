package mk.finki.ukim.emt.lab.persistence;

import mk.finki.ukim.emt.lab.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
}
