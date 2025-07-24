package com.alurachallenge.literatura_challenge_alura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private Integer numeroDeDescargas;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Autor> autores = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idiomas_libros", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idiomas;

    public Libro() {}

    public Libro(String titulo, Integer numeroDeDescargas, List<Autor> autores, List<String> idiomas) {
        this.titulo = titulo;
        this.numeroDeDescargas = numeroDeDescargas;
        this.idiomas = idiomas;
        this.autores = autores;
        this.autores.forEach(a -> a.setLibro(this));
    }

    public Libro(DatosLibro datos) {
        this.titulo = datos.titulo();
        this.numeroDeDescargas = datos.numeroDeDescargas();
        this.idiomas = datos.idioma();

        this.autores = datos.autor().stream()
                .map(a -> {
                    Autor autor = new Autor(a.name(), a.nacimiento(), a.fallecimiento());
                    autor.setLibro(this);
                    return autor;
                })
                .toList();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
        this.autores.forEach(a -> a.setLibro(this));
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- LIBRO -----\n");
        sb.append("Título: ").append(titulo).append("\n");

        if (autores != null && !autores.isEmpty()) {
            String nombresAutores = autores.stream()
                    .map(Autor::getNombre)
                    .reduce((a1, a2) -> a1 + ", " + a2)
                    .orElse("Desconocido");
            sb.append("Autor: ").append(nombresAutores).append("\n");
        } else {
            sb.append("Autor: Desconocido\n");
        }

        String idioma = (idiomas != null && !idiomas.isEmpty()) ? idiomas.get(0) : "N/A";
        sb.append("Idioma: ").append(idioma).append("\n");

        sb.append("Numero de descargas: ").append(numeroDeDescargas).append(".0\n");
        sb.append("-----------------\n");

        return sb.toString();
    }

}
