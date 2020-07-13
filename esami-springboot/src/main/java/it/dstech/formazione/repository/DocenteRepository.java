package it.dstech.formazione.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dstech.formazione.models.Docente;

public interface DocenteRepository extends JpaRepository<Docente,Long> {

}
