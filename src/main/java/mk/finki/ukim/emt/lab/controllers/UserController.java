package mk.finki.ukim.emt.lab.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import mk.finki.ukim.emt.lab.service.interfaces.IUserService;
import mk.finki.ukim.emt.lab.service.interfaces.IVerificationService;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;
import org.hibernate.annotations.GeneratorType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserController {
    private final IUserService _userService;
    private final IVerificationService _verificationService;

    UserController(IUserService userService, IVerificationService verificationService) {
        _userService = userService;
        _verificationService = verificationService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new RegisterViewModel());

        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword) throws Exception {
        RegisterViewModel user = new RegisterViewModel();

        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = password;
        user.confirmPassword = confirmPassword;

        UserResult result =  _userService.register(user);

        model.addAttribute("user", new RegisterViewModel());

        if (!result.isSuccessful()) {
            model.addAttribute("user", user);
            model.addAttribute("error", result.getMessage());
        } else {
            model.addAttribute("success", result.getMessage());
        }

        return "register";
    }

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Bad credentials");

        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(Model model, @RequestParam String email) {
        UserResult result = _userService.generatePassword(email);

        if (result.isSuccessful())
            model.addAttribute("success", result.getMessage());
        else
            model.addAttribute("error", result.getMessage());

        return "forgot-password";
    }

    @GetMapping("/activate")
    public String activate() {
        return "activate";
    }

    @PostMapping("/activate")
    public String activate(@RequestParam String token) {
        return "redirect:/activate/" + token;
    }

    @GetMapping("/activate/{token}")
    public String activate(Model model, @PathVariable String token) {
        if (_verificationService.verifyUser(token)) {
            model.addAttribute("success", "Your account has been activated");
            model.addAttribute("link", "/login");
            model.addAttribute("button", "Login");
        } else {
            model.addAttribute("error", "The provided verification code has expired or does not exist");
            model.addAttribute("link", "/register");
            model.addAttribute("button", "Register");
        }

        return "activate-link";
    }
}
