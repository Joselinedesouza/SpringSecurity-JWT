package it.epicode.SpringSecurity.JWT.controller;


import it.epicode.SpringSecurity.JWT.dto.PrenotazioneDTO;
import it.epicode.SpringSecurity.JWT.entity.Prenotazione;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @PostMapping
    public ResponseEntity<Prenotazione> creaPrenotazione(@RequestBody @Valid PrenotazioneDTO dto) {
        return ResponseEntity.ok(prenotazioneService.creaPrenotazione(dto));
    }

    @GetMapping("/mie")
    public ResponseEntity<List<Prenotazione>> getMiePrenotazioni() {
        return ResponseEntity.ok(prenotazioneService.getPrenotazioniUtenteCorrente());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaPrenotazione(@PathVariable Long id) {
        prenotazioneService.eliminaPrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}
