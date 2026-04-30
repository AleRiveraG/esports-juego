package com.juego.microservicio_juego.service;

import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.model.Juego;
import com.juego.microservicio_juego.repository.JuegoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JuegoService {

    private final JuegoRepository juegoRepository;

    private JuegoResponseDTO mapToDTO(Juego juego){
        return new JuegoResponseDTO(
            juego.getIdJuego(),
            juego.getNombre(),
            juego.getGenero(),
            juego.getDistribuidor(),
            juego.getPlataformas()
        );
    }

    public List<JuegoResponseDTO> obtenerJuegos(){
        return juegoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<JuegoResponseDTO> obtenerJuegoPorId(Long id){
        return juegoRepository.findById(id).map(this::mapToDTO);
    }

    public JuegoResponseDTO agregarJuego(JuegoRequestDTO dto){
        Juego juego = new Juego(null, dto.getNombre(), dto.getGenero(), dto.getDistribuidor(), dto.getPlataformas());
        return mapToDTO(juegoRepository.save(juego));
    }

    public Optional<JuegoResponseDTO> modificarJuego(Long id, JuegoRequestDTO dto){
        return juegoRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setGenero(dto.getGenero());
            existente.setDistribuidor(dto.getDistribuidor());
            existente.setPlataformas(dto.getPlataformas());
            return mapToDTO(juegoRepository.save(existente));
        });
    }

    public void eliminarJuego(Long id){
        juegoRepository.deleteById(id);
    }


}
