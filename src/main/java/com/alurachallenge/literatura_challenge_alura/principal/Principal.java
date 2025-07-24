package com.alurachallenge.literatura_challenge_alura.principal;

import com.alurachallenge.literatura_challenge_alura.model.Autor;
import com.alurachallenge.literatura_challenge_alura.model.DatosLibro;
import com.alurachallenge.literatura_challenge_alura.model.DatosRespuesta;
import com.alurachallenge.literatura_challenge_alura.model.Libro;
import com.alurachallenge.literatura_challenge_alura.repository.LibroRepository;
import com.alurachallenge.literatura_challenge_alura.service.ConsumoAPI;
import com.alurachallenge.literatura_challenge_alura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository libroRepository;
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    == MENÚ PRINCIPAL ==
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    0 - Salir
                    """);

            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 0 -> System.out.println("== Aplicación finalizada ==\n");
                default -> System.out.println("== Opción inválida ==\n");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("- Escribe el título del libro: ");
        String tituloBuscado = teclado.nextLine().trim();

        var libroExistente = libroRepository.findByTituloIgnoreCase(tituloBuscado);
        if (libroExistente.isPresent()) {
            System.out.println("== El libro ya está registrado ==\n");
            return;
        }

        String json = consumoAPI.obtenerDatos(URL_BASE + tituloBuscado.replace(" ", "+"));
        var datosRespuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (datosRespuesta != null && !datosRespuesta.resultados().isEmpty()) {
            DatosLibro datosLibro = datosRespuesta.resultados().get(0);

            Libro libro = new Libro();
            libro.setTitulo(datosLibro.titulo());
            libro.setNumeroDeDescargas(datosLibro.numeroDeDescargas());
            libro.setIdiomas(datosLibro.idioma());

            List<Autor> autores = datosLibro.autor().stream()
                    .map(a -> new Autor(a.name(), a.nacimiento(), a.fallecimiento()))
                    .toList();

            autores.forEach(a -> a.setLibro(libro));
            libro.setAutores(autores);
            libroRepository.save(libro);

            System.out.println("Libro encontrado y guardado:");
            System.out.println(libro);
        } else {
            System.out.println("No se encontró ningún libro con ese título.\n");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("== No hay libros registrados ==");
            return;
        }
        System.out.println("== Lista de libros registrados ==");
        libros.forEach(System.out::println);
    }

}
