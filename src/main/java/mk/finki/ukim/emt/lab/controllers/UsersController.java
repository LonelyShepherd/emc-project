package mk.finki.ukim.emt.lab.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.service.interfaces.IUsersService;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UsersController {
    private final IUsersService _usersService;

    UsersController(IUsersService usersService) {
        _usersService = usersService;
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new RegisterViewModel());

        return "register";
    }

    @PostMapping("/register")
    public String registerPost(WebRequest request, Model model) throws Exception {
        RegisterViewModel user = new RegisterViewModel();

        user.firstName = request.getParameter("firstName");
        user.lastName = request.getParameter("lastName");
        user.password = request.getParameter("password");
        user.confirmPassword = request.getParameter("confirmPassword");
        user.email = request.getParameter("email");

        UserResult result =  _usersService.create(user);

        model.addAttribute("user", new RegisterViewModel());

        if (!result.isSuccessful()) {
            model.addAttribute("user", user);
            model.addAttribute("error", result.getMessage());
        } else {
            model.addAttribute("success", result.getMessage());
        }

        return "register";
    }
}
