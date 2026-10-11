package com.escola.alunos.service;

import com.escola.alunos.dto.AlunoRequest;
import com.escola.alunos.dto.AlunoResponse;
import com.escola.alunos.entity.Aluno;
import com.escola.alunos.exception.AlunoNaoEncontradoException;
import com.escola.alunos.repository.AlunoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponse create(AlunoRequest request) {
        Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setIdade(request.getIdade());

        Aluno salvo = alunoRepository.save(aluno);
        return toResponse(salvo);
    }

    public List<AlunoResponse> findAll() {
        List<AlunoResponse> respostas = new ArrayList<>();
        for (Aluno aluno : alunoRepository.findAll()) {
            respostas.add(toResponse(aluno));
        }
        return respostas;
    }

    public AlunoResponse findById(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));
        return toResponse(aluno);
    }

    // PUT: busca o registro (lança exceção se o ID não existir), atualiza os campos e persiste.
    public void update(Long id, AlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setIdade(request.getIdade());

        alunoRepository.save(aluno);
    }

    // DELETE: verifica a existência com existsById() e usa if (!exists), sem lambda.
    public void deleteById(Long id) {
        boolean exists = alunoRepository.existsById(id);
        if (!exists) {
            throw new AlunoNaoEncontradoException(id);
        }
        alunoRepository.deleteById(id);
    }

    private AlunoResponse toResponse(Aluno aluno) {
        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getIdade());
    }
}
