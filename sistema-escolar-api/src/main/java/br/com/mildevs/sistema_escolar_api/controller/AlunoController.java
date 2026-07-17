package br.com.mildevs.sistema_escolar_api.controller;

import br.com.mildevs.sistema_escolar_api.DTO.AlunoDTO.AlunoRequestDTO;
import br.com.mildevs.sistema_escolar_api.DTO.AlunoDTO.AlunoResponseDTO;
import br.com.mildevs.sistema_escolar_api.entity.Aluno;
import br.com.mildevs.sistema_escolar_api.entity.Turma;
import br.com.mildevs.sistema_escolar_api.repository.AlunoRepository;
import br.com.mildevs.sistema_escolar_api.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    private AlunoResponseDTO toRespondeDTO(Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getMatricula(),
                aluno.getNome(),
                aluno.getSerie(),
                aluno.getDataNascimento()
        );
    }

    @PostMapping
    public AlunoResponseDTO criar(@RequestBody AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.getNome());
        aluno.setSerie(dto.getSerie());
        aluno.setDataNascimento(dto.getDataNascimento());
        Aluno salvo = alunoRepository.save(aluno);
        return toRespondeDTO(salvo);
    }

    @GetMapping
    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll()
                .stream()
                .map(this::toRespondeDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return alunoRepository.findById(id)
                .map(this::toRespondeDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable Integer id, @RequestBody AlunoRequestDTO dto) {
        Optional<Aluno> alunoOptional = alunoRepository.findById(id);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            aluno.setNome(dto.getNome());
            aluno.setSerie(dto.getSerie());
            aluno.setDataNascimento(dto.getDataNascimento());
            return ResponseEntity.ok(toRespondeDTO(alunoRepository.save(aluno)));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Vincular turma para aluno
    @PutMapping("/{matricula}/turmas/{codTurma}")
    public ResponseEntity<AlunoResponseDTO> adicionarTurma(@PathVariable Integer matricula, @PathVariable Integer codTurma) {
        Aluno aluno = alunoRepository.findById(matricula).orElse(null);
        Turma turma = turmaRepository.findById(codTurma).orElse(null);

        if (aluno == null || turma == null)
            return ResponseEntity.notFound().build();

        if (aluno.getTurmas() == null) {
            aluno.setTurmas(new ArrayList<>());
        }
        aluno.getTurmas().add(turma);
        return ResponseEntity.ok(toRespondeDTO(alunoRepository.save(aluno)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!alunoRepository.existsById(id))
            return ResponseEntity.notFound().build();
        else {
            alunoRepository.deleteById(id);
            return ResponseEntity.noContent().build();

        }

    }

    //Desvincular turma de aluno
    @DeleteMapping("/{matricula}/turmas/{codTurma}")
    public ResponseEntity<AlunoResponseDTO> removerTurma(@PathVariable Integer matricula, @PathVariable Integer codTurma) {
        Aluno aluno = alunoRepository.findById(matricula).orElse(null);
        if (aluno == null || aluno.getTurmas() == null) {
            return ResponseEntity.notFound().build();
        }

        aluno.getTurmas().removeIf(t -> t.getCodTurma().equals(codTurma));
        return ResponseEntity.ok(toRespondeDTO(alunoRepository.save(aluno)));
    }

}
