package com.juego.microservicio_juego.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juego.microservicio_juego.assemblers.JuegoModelAssembler;
import com.juego.microservicio_juego.client.AuditoriaClient;
import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.service.JuegoService;
import com.juego.microservicio_juego.service.PlataformaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

@WebMvcTest(JuegoController.class)
@Import(JuegoModelAssembler.class)
public class JuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JuegoService juegoService;

    @MockBean
    private PlataformaService plataformaService;

    @MockBean
    private AuditoriaClient auditoriaClient;

    private Plataforma plat;
    private JuegoResponseDTO responseDTO;
    private JuegoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        plat = new Plataforma();
        plat.setIdPlataforma(1L);
        plat.setNombrePlataforma("PC");

        responseDTO = new JuegoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Counter-Strike");
        responseDTO.setGenero("FPS");
        responseDTO.setDistribuidor("Valve");
        responseDTO.setPlataformaId(Set.of(plat.getIdPlataforma()));

        requestDTO = new JuegoRequestDTO();
        requestDTO.setNombre("Counter-Strike");
        requestDTO.setGenero("FPS");
        requestDTO.setDistribuidor("Valve");
        requestDTO.setIdPlataformas(Set.of(1L));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testBuscarPorId() throws Exception {
        when(juegoService.obtenerJuegoPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/juego/1")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Counter-Strike"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testListarJuegos() throws Exception {
        when(juegoService.obtenerJuegos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/juego")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGuardarJuego() throws Exception {
        when(juegoService.agregarJuego(any(JuegoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/juego")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testModificarJuego() throws Exception {
        when(juegoService.modificarJuego(eq(1L), any(JuegoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/juego/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Counter-Strike"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testEliminarJuego() throws Exception {
        when(juegoService.obtenerJuegoPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/juego/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(juegoService, times(1)).eliminarJuego(1L);
    }
}