package it.epicode.SpringSecurity.JWT.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneDTO {

    @NotNull
    private Long eventoId;

    private LocalDate dataPrenotazione = LocalDate.now();
    private String nota;
}
