package com.juego.microservicio_juego.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juego.microservicio_juego.assemblers.PlataformaModelAssembler;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.service.PlataformaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(PlataformaController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "ADMIN")
@Import(PlataformaModelAssembler.class)
public class PlataformaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlataformaService plataformaService;

    private Plataforma plat;

    @BeforeEach
    void setUp() {
        plat = new Plataforma();
        plat.setIdPlataforma(1L);
        plat.setNombrePlataforma("PC");
    }

    @Test
    void testObtenerTodo() throws Exception {
        when(plataformaService.obtenerPlataformas()).thenReturn(List.of(plat));

        mockMvc.perform(get("/api/plataforma")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(plataformaService.obtenerPorId(1L)).thenReturn(plat);

        mockMvc.perform(get("/api/plataforma/1")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPlataforma").value(1))
                .andExpect(jsonPath("$.nombrePlataforma").value("PC"))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(plataformaService).obtenerPorId(1L);
    }

    @Test
    void testAgregarPlataforma() throws Exception {
        when(plataformaService.agregarPlataforma(any(Plataforma.class))).thenReturn(plat);

        mockMvc.perform(post("/api/plataforma")
                        .with(csrf()) // Token CSRF requerido para POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plat))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPlataforma").value(1L))
                .andExpect(jsonPath("$._links.self.href").exists());

        verify(plataformaService).agregarPlataforma(any(Plataforma.class));
    }

    @Test
    void testEliminarPlataforma() throws Exception {
        when(plataformaService.obtenerPorId(1L)).thenReturn(plat);

        mockMvc.perform(delete("/api/plataforma/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(plataformaService, times(1)).eliminarPlataforma(1L);
    }
}