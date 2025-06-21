package it.epicode.SpringSecurity.JWT.service;


import it.epicode.SpringSecurity.JWT.dto.PrenotazioneDTO;
import it.epicode.SpringSecurity.JWT.entity.Evento;
import it.epicode.SpringSecurity.JWT.entity.Prenotazione;
import it.epicode.SpringSecurity.JWT.entity.User;
import it.epicode.SpringSecurity.JWT.exception.ElementoNonTrovatoException;
import it.epicode.SpringSecurity.JWT.exception.PostiEsauritiException;
import it.epicode.SpringSecurity.JWT.exception.PrenotazioneDuplicataException;
import it.epicode.SpringSecurity.JWT.repository.EventoRepository;
import it.epicode.SpringSecurity.JWT.repository.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenRepo;
    private final EventoRepository eventoRepo;
    private final JwtUtil jwtUtil;

    public Prenotazione creaPrenotazione(PrenotazioneDTO dto) {
        User utente = jwtUtil.getUtenteCorrente();
        Evento evento = eventoRepo.findById(dto.getEventoId())
                .orElseThrow(() -> new ElementoNonTrovatoException("Evento non trovato"));

        if (evento.getPostiDisponibili() <= 0) {
            throw new PostiEsauritiException("Posti esauriti per questo evento");
        }

        boolean giàPrenotato = prenRepo.existsByUtenteIdAndEventoId(utente.getId(), evento.getId());
        if (giàPrenotato) {
            throw new PrenotazioneDuplicataException("Hai già prenotato questo evento");
        }

        Prenotazione p = Prenotazione.builder()
                .utente(utente)
                .evento(evento)
                .dataPrenotazione(dto.getDataPrenotazione())
                .build();

        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoRepo.save(evento);

        return prenRepo.save(p);
    }

    public List<Prenotazione> getPrenotazioniUtenteCorrente() {
        User utente = jwtUtil.getUtenteCorrente();
        return prenRepo.findByUtenteId(utente.getId());
    }

    public void eliminaPrenotazione(Long id) {
        prenRepo.deleteById(id);
    }
}
