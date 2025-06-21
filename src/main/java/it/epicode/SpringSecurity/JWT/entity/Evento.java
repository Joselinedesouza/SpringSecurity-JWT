package it.epicode.SpringSecurity.JWT.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titolo;

    private String descrizione;

    private LocalDate data;

    private String luogo;

    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "creatore_id")
    private User creatore;
}
