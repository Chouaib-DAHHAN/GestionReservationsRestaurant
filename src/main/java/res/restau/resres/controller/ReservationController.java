package res.restau.resres.controller;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import res.restau.resres.model.Reservation;
import res.restau.resres.model.User;
import res.restau.resres.repository.ReservationRepository;
import res.restau.resres.repository.TableRestaurantRepository;
import res.restau.resres.service.ReservationService;

import java.util.Map;
import java.util.Optional;
import java.time.Duration;



@Controller
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final TableRestaurantRepository tableRestaurantRepository;
    private final ReservationService reservationService;

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
                              @AuthenticationPrincipal User user, Model model) {
    reservation.setUser(user);

    // Recharge complètement la table depuis la base
    var tableId = reservation.getTable().getId();
    var table = tableRestaurantRepository.findById(tableId)
                    .orElseThrow(() -> new IllegalArgumentException("Table introuvable"));

    table.setDisponible(false);
    tableRestaurantRepository.save(table);

    // Remet la table dans la réservation (hydration complète)
    reservation.setTable(table);

    // Fixer une durée par défaut si vide
    if (reservation.getDureeMinutes() == 0) {
        reservation.setDureeMinutes(120);
    }

    reservationRepository.save(reservation);

    model.addAttribute("success", "Réservation enregistrée avec succès !");
    return "redirect:/reservations/list";
}


    // 3) Liste des réservations : GET /reservations/list
    @GetMapping("/list")
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservation/list";  // corresponds to templates/reservation/list.html
    }

    // 4) Edition d'une réservation : GET /reservations/edit/{id}
    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation invalide : " + id));
        model.addAttribute("reservation", reservation);
        model.addAttribute("tables", tableRestaurantRepository.findAll());
        return "reservation/form";  // corresponds to templates/reservation/form.html
    }

    // 5) Suppression d'une réservation : GET /reservations/delete/{id}
    @GetMapping("/delete/{id}")
public String deleteReservation(@PathVariable Long id) {
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if (reservation != null) {
        // Remettre la table disponible
        reservation.getTable().setDisponible(true);
        tableRestaurantRepository.save(reservation.getTable());

        reservationRepository.deleteById(id);
    }
    return "redirect:/reservations/list";
}


    @GetMapping("/check")
@ResponseBody
public Map<String, Object> checkTableAvailability(
    @RequestParam Long tableId,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
    @RequestParam(defaultValue = "120") int dureeMinutes) {
    
    Map<String, Object> response = new HashMap<>();

    Optional<Reservation> conflict = reservationService.getConflictingReservation(tableId, dateTime, dureeMinutes);
    if (conflict.isPresent()) {
        LocalDateTime fin = conflict.get().getDateTime().plusMinutes(conflict.get().getDureeMinutes());
        long minutesRestantes = Duration.between(LocalDateTime.now(), fin).toMinutes();
        response.put("available", false);
        response.put("remaining", minutesRestantes);
    } else {
        response.put("available", true);
    }

    return response;
}

}