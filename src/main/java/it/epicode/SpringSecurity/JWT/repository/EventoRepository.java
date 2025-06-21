package it.epicode.SpringSecurity.JWT.repository;

import it.epicode.SpringSecurity.JWT.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
