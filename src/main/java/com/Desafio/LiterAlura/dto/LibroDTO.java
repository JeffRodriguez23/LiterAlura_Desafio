package com.Desafio.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
                           String title,
                           List<String> languages,
                           @JsonProperty("download_count") int downloadCount,
                           List<AutorDTO> authors) {
}
