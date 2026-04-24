package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.model.Juego;
import com.juego.microservicio_juego.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juego")
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public List<Juego> listarJuego(){
        return juegoService.obtenerJuegos();
    }

    @PostMapping
    public Juego guardarJuego(@RequestBody Juego juego){
        return juegoService.guardarJuego(juego);
    }

    @PutMapping("{id}")
    public Juego modificarJuego(@PathVariable Long id, @RequestBody Juego juego){
        return juegoService.modificarJuego(id, juego);
    }

    @DeleteMapping("{id}")
    public void eliminarJuego(@PathVariable Long id){
        juegoService.eliminarJuego(id);
    }
}
