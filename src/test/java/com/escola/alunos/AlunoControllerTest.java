package com.escola.alunos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.escola.alunos.dto.AlunoRequest;
import com.escola.alunos.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    void limparBanco() {
        alunoRepository.deleteAll();
    }

    private String json(String nome, String email, int idade) throws Exception {
        return objectMapper.writeValueAsString(new AlunoRequest(nome, email, idade));
    }

    private long criarAluno(String nome) throws Exception {
        String corpo = mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(nome, "aluno@escola.com", 20)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(corpo).get("id").asLong();
    }

    @Test
    void putAtualizaAlunoExistenteERetorna204() throws Exception {
        long id = criarAluno("Maria");

        mockMvc.perform(put("/alunos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Maria Silva", "maria@escola.com", 21)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/alunos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Silva"))
                .andExpect(jsonPath("$.email").value("maria@escola.com"))
                .andExpect(jsonPath("$.idade").value(21));
    }

    @Test
    void putComIdInexistenteRetorna404() throws Exception {
        mockMvc.perform(put("/alunos/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Ninguém", "x@escola.com", 30)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRemoveAlunoExistenteERetorna204() throws Exception {
        long id = criarAluno("João");

        mockMvc.perform(delete("/alunos/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/alunos/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteComIdInexistenteRetorna404() throws Exception {
        mockMvc.perform(delete("/alunos/9999"))
                .andExpect(status().isNotFound());
    }
}
