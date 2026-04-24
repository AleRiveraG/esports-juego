package com.juego.microservicio_juego.service;

import com.juego.microservicio_juego.model.Juego;
import com.juego.microservicio_juego.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> obtenerJuegos(){
        return juegoRepository.listarJuegos();
    }

    public Juego buscarPorId(Long id){
        return juegoRepository.buscarPorId(id);
    }

    public Juego guardarJuego(Juego juego){
        return juegoRepository.guardarJuego(juego);
    }

    public Juego modificarJuego(Long id, Juego juego){
        return juegoRepository.modificarJuego(id, juego);
    }

    public void eliminarJuego(Long id){
        juegoRepository.eliminarJuego(id);
    }
}
