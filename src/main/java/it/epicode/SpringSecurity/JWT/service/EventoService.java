package it.epicode.SpringSecurity.JWT.service;



import it.epicode.SpringSecurity.JWT.dto.EventoDTO;
import it.epicode.SpringSecurity.JWT.entity.Evento;
import it.epicode.SpringSecurity.JWT.entity.User;
import it.epicode.SpringSecurity.JWT.exception.ElementoNonTrovatoException;
import it.epicode.SpringSecurity.JWT.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepo;
    private final JwtUtil jwtUtil;

    public List<Evento> getAllEventi() {
        return eventoRepo.findAll();
    }

    public Evento getEventoById(Long id) {
        return eventoRepo.findById(id)
                .orElseThrow(() -> new ElementoNonTrovatoException("Evento non trovato"));
    }

    public Evento creaEvento(EventoDTO dto) {
        User creatore = jwtUtil.getUtenteCorrente();
        Evento evento = Evento.builder()
                .titolo(dto.getTitolo())
                .descrizione(dto.getDescrizione())
                .data(dto.getData())
                .luogo(dto.getLuogo())
                .postiDisponibili(dto.getPostiDisponibili())
                .creatore(creatore)
                .build();

        return eventoRepo.save(evento);
    }

    public Evento aggiornaEvento(Long id, EventoDTO dto) {
        Evento evento = getEventoById(id);
        evento.setTitolo(dto.getTitolo());
        evento.setDescrizione(dto.getDescrizione());
        evento.setData(dto.getData());
        evento.setLuogo(dto.getLuogo());
        evento.setPostiDisponibili(dto.getPostiDisponibili());
        return eventoRepo.save(evento);
    }

    public void eliminaEvento(Long id) {
        if (!eventoRepo.existsById(id)) {
            throw new ElementoNonTrovatoException("Evento non trovato");
        }
        eventoRepo.deleteById(id);
    }
}
