package it.epicode.SpringSecurity.JWT.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDTO {

    @NotBlank
    private String titolo;

    @NotBlank
    private String descrizione;

    @Future
    private LocalDate data;

    @NotBlank
    private String luogo;

    @Min(1)
    private int postiDisponibili;
}
