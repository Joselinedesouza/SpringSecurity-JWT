package it.epicode.SpringSecurity.JWT.repository;

import it.epicode.SpringSecurity.JWT.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtenteId(Long utenteId);
    boolean existsByUtenteIdAndEventoId(Long utenteId, Long eventoId);
}
