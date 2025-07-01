package res.restau.resres.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import res.restau.resres.model.Reservation;
import res.restau.resres.model.User;
import res.restau.resres.repository.ReservationRepository;
import res.restau.resres.repository.TableRestaurantRepository;
@Controller
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final TableRestaurantRepository tableRestaurantRepository;

    // 1) Formulaire de création : GET /reservations/new
    @GetMapping("/new")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("tables", tableRestaurantRepository.findAll());
        return "reservation/form";  // corresponds to templates/reservation/form.html
    }

    // 2) Sauvegarde de la réservation : POST /reservations/save
    @PostMapping("/save")
    public String saveReservation(@ModelAttribute Reservation reservation,
                                  @AuthenticationPrincipal User user) {
        reservation.setUser(user);
        reservationRepository.save(reservation);
        return "redirect:/reservations/list";
    }

    // 3) Liste des réservations : GET /reservations/list
    @GetMapping("/list")
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservation/list";  // corresponds to templates/reservation/list.html
    }
}