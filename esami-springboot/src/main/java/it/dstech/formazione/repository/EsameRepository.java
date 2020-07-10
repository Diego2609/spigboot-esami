package it.dstech.formazione.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dstech.formazione.models.Esame;

public interface EsameRepository extends JpaRepository<Esame, Long> {

	List<Esame> findByIdUtenteOrderByVoto(Long idUtente);

}
