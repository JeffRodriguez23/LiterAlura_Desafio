package com.Desafio.LiterAlura.service;

public interface IConvertirDato {

    <T> T obtenerDatos(String json, Class<T> clase);
}
