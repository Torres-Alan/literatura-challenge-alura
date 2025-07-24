package com.alurachallenge.literatura_challenge_alura.repository;

import com.alurachallenge.literatura_challenge_alura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    List<Libro> findByIdiomasContainingIgnoreCase(String idioma);
}
