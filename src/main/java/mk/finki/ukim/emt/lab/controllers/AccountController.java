package mk.finki.ukim.emt.lab.controllers;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.service.interfaces.IUserService;
import mk.finki.ukim.emt.lab.service.results.PasswordResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/account")
@PreAuthorize("hasAnyRole('USER')")
public class AccountController {
    private final IUserService _userService;

    public AccountController(IUserService userService) {
        _userService = userService;
    }

    @GetMapping
    public String account(Model model, Principal principal) {
        model.addAttribute("user", _userService.findByEmail(principal.getName()));

        return "account";
    }

    @PostMapping
    public String updateAccount(Model model, Principal principal, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        User user = _userService.findByEmail(principal.getName());

        if (user == null) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Something went wrong");

            return "account";
        }

        User updated = _userService.updateUserInfo(user, firstName, lastName, email);

        if (updated != null) {
            model.addAttribute("user", updated);
            model.addAttribute("success", "Your changes has been saved");
        }

        return "account";
    }

    @PostMapping("change-password")
    public String changePassword(Model model, Principal principal, @RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmNewPassword) {
        User user = _userService.findByEmail(principal.getName());

        model.addAttribute("user", user);

        PasswordResult result = _userService.changePassword(user, currentPassword, newPassword, confirmNewPassword);

        if (result.isSuccessful())
            model.addAttribute("passwordSuccess", result.getMessage());
        else
            model.addAttribute("passwordError", result.getMessage());

        return "account";
    }
}
