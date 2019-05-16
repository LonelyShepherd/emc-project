package mk.finki.ukim.emt.lab.persistence;

import mk.finki.ukim.emt.lab.models.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByName(String name);
}
