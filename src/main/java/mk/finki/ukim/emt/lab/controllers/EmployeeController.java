package mk.finki.ukim.emt.lab.controllers;

import mk.finki.ukim.emt.lab.consts.Role;
import mk.finki.ukim.emt.lab.models.entities.Manager;
import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.service.interfaces.IEmployeeService;
import mk.finki.ukim.emt.lab.service.interfaces.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyRole('Manager, Admin')")
public class EmployeeController {
    private final IUserService _userService;
    private final IEmployeeService _employeeService;

    public EmployeeController(IUserService userService, IEmployeeService employeeService) {
        _userService = userService;
        _employeeService = employeeService;
    }

    @GetMapping("manage")
    public String manage(Model model, Principal principal, @RequestParam(required = false) Integer id) {
        User current = _userService.findByEmail(principal.getName());
        User user = null;
        List<User> users = new ArrayList<>();

        boolean canEdit = true;

        if (current.role == Role.Admin)
            users = _userService.find(u -> u.id != current.id);

        if (current.role == Role.Manager) {
            id = null;
            canEdit = false;
            Manager manager = _employeeService.findManager(principal.getName());

            users = _userService.find(u -> u.role == Role.Employee && manager.department.equals(u.department));
        }

        if (id != null)
            user = _userService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("canEdit", canEdit);

        return "manage-employees";
    }

    @PostMapping("manage")
    public String delete(@RequestParam int id) {
        _userService.deleteById(id);

        return "redirect:/employee/manage";
    }

    @PostMapping("manage/edit")
    @PreAuthorize("hasAnyRole('Admin')")
    public String edit(@RequestParam Integer id, @RequestParam String role, @RequestParam String department) {
        _employeeService.updateEmployeeInfo(id, role, department);

        return "redirect:/employee/manage";
    }
}
