package com.juego.microservicio_juego.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoResponseDTO {

    private Long id;
    private String nombre;
    private String genero;
    private String distribuidor;
    private String plataformas;

}
