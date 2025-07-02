package res.restau.resres.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private int nombrePersonnes;

    private String commentaire;

    @Column(name = "duree_minutes")
    private int dureeMinutes = 120;


    @ManyToOne
    private User user;

    @ManyToOne
    private TableRestaurant table;
}
