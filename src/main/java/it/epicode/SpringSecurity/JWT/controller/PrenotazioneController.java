package it.epicode.SpringSecurity.JWT.controller;

import it.epicode.SpringSecurity.JWT.dto.PrenotazioneDTO;
import it.epicode.SpringSecurity.JWT.entity.Prenotazione;
import it.epicode.SpringSecurity.JWT.entity.User;
import it.epicode.SpringSecurity.JWT.service.PrenotazioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    // Crea una prenotazione per l'utente autenticato
    @PostMapping
    public ResponseEntity<Prenotazione> creaPrenotazione(
            @RequestBody @Valid PrenotazioneDTO dto,
            @AuthenticationPrincipal User utenteAutenticato) {
        return ResponseEntity.ok(prenotazioneService.creaPrenotazione(dto, utenteAutenticato));
    }

    // Recupera le prenotazioni dell'utente autenticato
    @GetMapping("/mie")
    public ResponseEntity<List<Prenotazione>> getMiePrenotazioni(@AuthenticationPrincipal User utenteAutenticato) {
        return ResponseEntity.ok(prenotazioneService.getPrenotazioniUtente(utenteAutenticato));
    }

    // Elimina una prenotazione effettuata dall'utente autenticato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaPrenotazione(
            @PathVariable Long id,
            @AuthenticationPrincipal User utenteAutenticato) {
        prenotazioneService.eliminaPrenotazione(id, utenteAutenticato);
        return ResponseEntity.noContent().build();
    }
}
