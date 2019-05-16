package mk.finki.ukim.emt.lab.service.interfaces;

import mk.finki.ukim.emt.lab.models.entities.Manager;

public interface IEmployeeService {
    Manager findManager(String email);
    void updateEmployeeInfo(int id, String role, String department);
}
