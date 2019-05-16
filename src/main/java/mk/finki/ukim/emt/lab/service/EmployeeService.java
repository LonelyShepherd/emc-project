package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.consts.Role;
import mk.finki.ukim.emt.lab.models.entities.Employee;
import mk.finki.ukim.emt.lab.models.entities.Manager;
import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.persistence.IDepartmentRepository;
import mk.finki.ukim.emt.lab.persistence.IEmployeeRepository;
import mk.finki.ukim.emt.lab.persistence.IManagerRepository;
import mk.finki.ukim.emt.lab.persistence.IUserRepository;
import mk.finki.ukim.emt.lab.service.interfaces.IEmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService {
    private final IManagerRepository _managerRepository;
    private final IUserRepository _userRepository;
    private final IDepartmentRepository _departmentRepository;
    private final IEmployeeRepository _employeeRepository;

    public EmployeeService(
            IManagerRepository managerRepository,
            IUserRepository userRepository,
            IDepartmentRepository departmentRepository,
            IEmployeeRepository employeeRepository
    ) {
        _managerRepository = managerRepository;
        _userRepository = userRepository;
        _departmentRepository = departmentRepository;
        _employeeRepository = employeeRepository;
    }

    @Override
    public Manager findManager(String email) {
        return _managerRepository.findByEmail(email).orElseGet(null);
    }

    @Override
    public void updateEmployeeInfo(int id, String role, String department) {
        User user = _userRepository.findById(id).orElseGet(null);

        if (user == null)
            return;

        switch (role.toLowerCase()) {
            case "employee":
                if (user.role == Role.Employee) {
                    Employee e = _employeeRepository.findById(id).orElseGet(null);
                    e.role = Role.Employee;
                    e.department = department != null && department != "" ? _departmentRepository.findByName(department).orElseGet(null) : e.department;
                    _employeeRepository.save(e);
                }

                if (user.role == Role.Manager) {
                    Manager m = _managerRepository.findById(id).orElseGet(null);
                    m.role = Role.Employee;
                    m.department = department != null && department != "" ? _departmentRepository.findByName(department).orElseGet(null) : m.department;
                    _managerRepository.save(m);
                }

                if (user.role == Role.User) {
                    User u = _userRepository.findById(id).orElseGet(null);
                    u.role = Role.Employee;
                    u.department = department != null && department != "" ? _departmentRepository.findByName(department).orElseGet(null) : u.department;
                    _userRepository.save(u);
                }
                break;
            case "manager":
                if (user.role == Role.Employee) {
                    Employee e = _employeeRepository.findById(id).orElseGet(null);
                    e.role = Role.Manager;
                    e.department = department != null && department != "" ? _departmentRepository.findByName(department).orElseGet(null) : e.department;
                    _employeeRepository.save(e);
                }

                if (user.role == Role.Manager) {
                    Manager m = _managerRepository.findById(id).orElseGet(null);
                    m.role = Role.Manager;
                    m.department = department != null && department != "" ? _departmentRepository.findByName(department).orElseGet(null) : m.department;
                    _managerRepository.save(m);
                }

                if (user.role == Role.User) {
                    User u = _userRepository.findById(id).orElseGet(null);
                    u.role = Role.Manager;
                    u.department = department != null && department != "" ? _departmentRepository.findByName(department).orElseGet(null) : u.department;
                    _userRepository.save(u);
                }
            case "user":
                if (user.role == Role.Employee) {
                    Employee e = _employeeRepository.findById(id).orElseGet(null);
                    e.role = Role.User;
                    e.department = null;
                    _employeeRepository.save(e);
                }

                if (user.role == Role.Manager) {
                    Manager m = _managerRepository.findById(id).orElseGet(null);
                    m.role = Role.User;
                    m.department = null;
                    _managerRepository.save(m);
                }

                if (user.role == Role.User) {
                    User u = _userRepository.findById(id).orElseGet(null);
                    u.role = Role.User;
                    u.department = null;
                    _userRepository.save(u);
                }
                break;
        }
    }
}
