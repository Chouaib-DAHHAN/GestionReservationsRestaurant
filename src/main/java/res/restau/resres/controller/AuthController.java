package res.restau.resres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import res.restau.resres.dto.RegisterRequest;
import res.restau.resres.model.Role;
import res.restau.resres.model.User;
import res.restau.resres.repository.RoleRepository;
import res.restau.resres.repository.UserRepository;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") RegisterRequest request, Model model) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            model.addAttribute("errorMessage", "Nom d'utilisateur déjà utilisé !");
            return "auth/register";
        }

        Role role = roleRepository.findByName(request.getRole().toUpperCase()).orElse(null);
        if (role == null) {
            model.addAttribute("errorMessage", "Rôle invalide !");
            return "auth/register";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);

        model.addAttribute("successMessage", "Compte créé avec succès ! Vous pouvez maintenant vous connecter.");
        model.addAttribute("registerRequest", new RegisterRequest()); // Reset form

        return "auth/register";
    }
}