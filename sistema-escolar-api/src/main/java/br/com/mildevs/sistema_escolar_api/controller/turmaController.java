package br.com.mildevs.sistema_escolar_api.controller;

import br.com.mildevs.sistema_escolar_api.DTO.AlunoDTO.AlunoResumoDTO;
import br.com.mildevs.sistema_escolar_api.DTO.ProfessorDTO.ProfessorResumoDTO;
import br.com.mildevs.sistema_escolar_api.DTO.Turma.TurmaRequestDTO;
import br.com.mildevs.sistema_escolar_api.DTO.Turma.TurmaResponseDTO;
import br.com.mildevs.sistema_escolar_api.repository.TurmaRepository;
import br.com.mildevs.sistema_escolar_api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.mildevs.sistema_escolar_api.entity.Turma;
import br.com.mildevs.sistema_escolar_api.entity.Professor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/turmas")
public class turmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    private TurmaResponseDTO toResponseDTO(Turma turma) {
        ProfessorResumoDTO professorDTO = null;
        if (turma.getProfessor() != null) {
            professorDTO = new ProfessorResumoDTO(
                    turma.getProfessor().getCodProfessor(),
                    turma.getProfessor().getNome()
            );
        }

        List<AlunoResumoDTO> alunosDTO = Collections.emptyList();
        if (turma.getAlunos() != null) {
            alunosDTO = turma.getAlunos().stream()
                    .map(a -> new AlunoResumoDTO(a.getMatricula(), a.getNome()))
                    .collect(Collectors.toList());
        }

        return new TurmaResponseDTO(turma.getCodTurma(), turma.getNome(), professorDTO, alunosDTO);
    }

    @PostMapping
    public ResponseEntity<TurmaResponseDTO> criar(@RequestBody TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setNome(dto.getNome());

        if (dto.getCodProfessor() != null) {
            Professor professor = professorRepository.findById(dto.getCodProfessor()).orElse(null);
            if (professor == null) {
                return ResponseEntity.badRequest().build();
            }
            turma.setProfessor(professor);
        }
        Turma salva = turmaRepository.save(turma);
        return ResponseEntity.ok(toResponseDTO(salva));
    }

    @GetMapping
    public List<TurmaResponseDTO> listarTurma() {
        return turmaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{cod}")
    public ResponseEntity<TurmaResponseDTO> buscarPorId(@PathVariable Integer cod) {
        return turmaRepository.findById(cod)
                .map(this::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{codTurma}/professor/{codProfessor}")
    public ResponseEntity<TurmaResponseDTO> vincularProfessor(@PathVariable Integer codTurma, @PathVariable Integer codProfessor) {
        Turma turma = turmaRepository.findById(codTurma).orElse(null);
        Professor professor = professorRepository.findById(codProfessor).orElse(null);

        if (turma == null || professor == null)
            return ResponseEntity.notFound().build();

        turma.setProfessor(professor);
        return ResponseEntity.ok(toResponseDTO(turmaRepository.save(turma)));
    }

    @PutMapping("/{cod}")
    public ResponseEntity<TurmaResponseDTO> atualizar_nome(@PathVariable Integer cod, @RequestBody TurmaResponseDTO turmaAtualizado) {
        return turmaRepository.findById(cod)
                .map(turma -> {
                    turma.setNome(turmaAtualizado.getNome());
                    return ResponseEntity.ok(toResponseDTO(turmaRepository.save(turma)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cod}")
    public ResponseEntity<Void> deletar(@PathVariable Integer cod) {
        if (!turmaRepository.existsById(cod))
            return ResponseEntity.notFound().build();
        else {
            turmaRepository.deleteById(cod);
            return ResponseEntity.noContent().build();

        }

    }

    @DeleteMapping("/{cod}/professor")
    public ResponseEntity<TurmaResponseDTO> desvincularProfessor(@PathVariable Integer cod) {
        Turma turma = turmaRepository.findById(cod).orElse(null);
        if (turma == null)
            return ResponseEntity.notFound().build();

        turma.setProfessor(null);
        return ResponseEntity.ok(toResponseDTO(turmaRepository.save(turma)));
    }

}
