package res.restau.resres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import res.restau.resres.model.User;
import res.restau.resres.service.UserService;

@Controller
@RequestMapping("/admin/clients")

public class ClientController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", userService.getAllClients());
        return "admin/clients/list";
    }

    @GetMapping("/new")
    public String newClientForm(Model model) {
        model.addAttribute("client", new User());
        return "admin/clients/form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") User client) {
        userService.saveClient(client);
        return "redirect:/admin/clients";
    }

    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable Long id, Model model) {
        model.addAttribute("client", userService.getClientById(id).orElseThrow());
        return "admin/clients/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        userService.deleteClient(id);
        return "redirect:/admin/clients";
    }
}
