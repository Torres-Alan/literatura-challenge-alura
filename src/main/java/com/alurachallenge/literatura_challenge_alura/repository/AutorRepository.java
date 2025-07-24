package com.alurachallenge.literatura_challenge_alura.repository;

import com.alurachallenge.literatura_challenge_alura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
