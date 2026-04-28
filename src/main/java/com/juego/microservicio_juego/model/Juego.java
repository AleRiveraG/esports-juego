package com.juego.microservicio_juego.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juego {

    @NotNull(message = "El id no puede ser nulo")
    @Positive(message = "El id tiene que ser mayor a 0")
    private Long idJuego;

    @NotBlank(message = "El nombre no puede ser vacio ni nulo")
    private String nombre;

    @NotBlank(message = "El genero no puede ser vacio ni nulo")
    private String genero;

    private String distribuidor;

    @NotBlank(message = "La plataforma no puede ser vacia ni nulo")
    private String plataformas;

}
