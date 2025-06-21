package it.epicode.SpringSecurity.JWT.controller;


import it.epicode.SpringSecurity.JWT.entity.Evento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<Evento>> getAll() {
        return ResponseEntity.ok(eventoService.getAllEventi());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.getEventoById(id));
    }

    @PostMapping
    public ResponseEntity<Evento> creaEvento(@RequestBody @Valid EventoDTO dto) {
        return ResponseEntity.ok(eventoService.creaEvento(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> aggiornaEvento(@PathVariable Long id, @RequestBody @Valid EventoDTO dto) {
        return ResponseEntity.ok(eventoService.aggiornaEvento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaEvento(@PathVariable Long id) {
        eventoService.eliminaEvento(id);
        return ResponseEntity.noContent().build();
    }
}
