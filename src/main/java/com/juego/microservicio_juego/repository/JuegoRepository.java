package com.juego.microservicio_juego.repository;

import com.juego.microservicio_juego.model.Juego;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JuegoRepository {

    private List<Juego> listaJuegos = new ArrayList<>();

    //Listar juegos

    public List<Juego> listarJuegos(){
        return listaJuegos;
    }

    //Obtener juego por id

    public Juego buscarPorId(Long id){
        for(Juego juego : listaJuegos){
            if(juego.getIdJuego().equals(id)){
                return juego;
            }
        }
        return null;
    }

    //Guardar juego

    public Juego guardarJuego(Juego juego){
        listaJuegos.add(juego);
        return juego;
    }

    //Modificar juego

    public Juego modificarJuego(Long id, Juego juego){
        for(Juego juego1: listaJuegos){
            if(juego1.getIdJuego().equals(id)){
                juego1.setIdJuego(juego.getIdJuego());
                juego1.setNombre(juego.getNombre());
                juego1.setGenero(juego.getGenero());
                juego1.setDistribuidor(juego.getDistribuidor());
                juego1.setPlataformas(juego.getPlataformas());

                return juego1;
            }
        }
        return null;
    }

    //Eliminar juego

    public void eliminarJuego(Long id){
        Juego juego = buscarPorId(id);

        if(juego != null){
            listaJuegos.remove(juego);
        }
    }

}
