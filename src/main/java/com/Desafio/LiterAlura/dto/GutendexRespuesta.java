package com.Desafio.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexRespuesta(List<LibroDTO> results) {
}
