package com.juego.microservicio_juego.repository;

import com.juego.microservicio_juego.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlataformaRepository extends JpaRepository <Plataforma, Long> {
}
