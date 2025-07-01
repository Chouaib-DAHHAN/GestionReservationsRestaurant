package res.restau.resres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import res.restau.resres.dto.RegisterRequest;

@Controller
public class AuthPageController {

    @GetMapping("/login")
    public String login() {
        return "auth/login";  // en supposant que login.html est dans templates/auth/
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @GetMapping("/dashboard")
public String dashboard() {
    return "auth/dashboard";  // en supposant que dashboard.html est dans templates/layout/
}


}
