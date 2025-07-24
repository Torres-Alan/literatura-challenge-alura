package com.alurachallenge.literatura_challenge_alura.repository;

import com.alurachallenge.literatura_challenge_alura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqualOrFallecimientoIsNull(Integer año1, Integer año2);
}
