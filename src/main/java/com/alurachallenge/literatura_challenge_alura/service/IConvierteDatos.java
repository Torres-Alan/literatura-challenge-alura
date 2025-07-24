package com.alurachallenge.literatura_challenge_alura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
