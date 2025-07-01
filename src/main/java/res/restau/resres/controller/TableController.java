package res.restau.resres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import res.restau.resres.model.TableRestaurant;
import res.restau.resres.service.TableService;


@Controller
@RequestMapping("/admin/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping
    public String listTables(Model model) {
        model.addAttribute("tables", tableService.findAll());
        return "admin/tables/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("table", new TableRestaurant());
        return "admin/tables/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TableRestaurant table, Model model) {
        if (table.getId() == null && tableService.findAll().stream()
            .anyMatch(t -> t.getNumeroTable() == table.getNumeroTable())) {
            model.addAttribute("error", "Numéro de table déjà utilisé !");
            model.addAttribute("table", table);
            return "admin/tables/form";
        }

        tableService.save(table);
        return "redirect:/admin/tables";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        TableRestaurant table = tableService.findById(id).orElseThrow();
        model.addAttribute("table", table);
        return "admin/tables/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tableService.deleteById(id);
        return "redirect:/admin/tables";
    }
}
