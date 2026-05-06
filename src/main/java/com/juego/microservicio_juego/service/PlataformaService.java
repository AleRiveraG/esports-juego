package com.juego.microservicio_juego.service;

import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlataformaService {

    private final PlataformaRepository plataformaRepository;

    public List<Plataforma> obtenerPlataformas(){
        return plataformaRepository.findAll();
    }

    public Plataforma agregarPlataforma(Plataforma plataforma){
        plataformaRepository.save(plataforma);
        return plataforma;
    }

    public Optional<Plataforma> obtenerPorId(Long id){
        return plataformaRepository.findById(id);
    }

    public void eliminarPlataforma(Long id){
        plataformaRepository.deleteById(id);
    }



}
