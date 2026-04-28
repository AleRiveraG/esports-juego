package com.juego.microservicio_juego.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoRequestDTO {

    @NotBlank(message = "El nombre no puede ser vacio ni nulo")
    private String nombre;

    @NotBlank(message = "El genero no puede ser vacio ni nulo")
    private String genero;

    private String distribuidor;

    @NotBlank(message = "La plataforma no puede ser vacio ni nulo")
    private String plataformas;
}
