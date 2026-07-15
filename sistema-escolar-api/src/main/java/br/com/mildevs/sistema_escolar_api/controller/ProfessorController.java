package br.com.mildevs.sistema_escolar_api.controller;


import br.com.mildevs.sistema_escolar_api.DTO.ProfessorDTO.ProfessorRequestDTO;
import br.com.mildevs.sistema_escolar_api.DTO.ProfessorDTO.ProfessorResponseDTO;
import br.com.mildevs.sistema_escolar_api.entity.Professor;
import br.com.mildevs.sistema_escolar_api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    private ProfessorResponseDTO toResponseDTO(Professor prof) {
        return new ProfessorResponseDTO(
                prof.getCodProfessor(),
                prof.getNome(),
                prof.getTelefone(),
                prof.getNivelGraduacao(),
                prof.getSalario(),
                prof.getDisciplina()
        );
    }

    @PostMapping
    public ProfessorResponseDTO criar(@RequestBody ProfessorRequestDTO dto) {
        Professor prof = new Professor();
        prof.setNome(dto.getNome());
        prof.setTelefone(dto.getTelefone());
        prof.setNivelGraduacao(dto.getNivelGraduacao());
        prof.setSalario(dto.getSalario());
        prof.setDisciplina(dto.getDisciplina());

        Professor salvo = professorRepository.save(prof);
        return toResponseDTO(salvo);
    }

    @GetMapping
    public List<ProfessorResponseDTO> listarTodos() {
        return professorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/{cod}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Integer cod) {
        return professorRepository.findById(cod)
                .map(this::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cod}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(@PathVariable Integer cod, @RequestBody ProfessorRequestDTO dto) {
        return professorRepository.findById(cod)
                .map(prof -> {
                    prof.setNome(dto.getNome());
                    prof.setTelefone(dto.getTelefone());
                    prof.setNivelGraduacao(dto.getNivelGraduacao());
                    prof.setSalario(dto.getSalario());
                    prof.setDisciplina(dto.getDisciplina());
                    Professor atualizado = professorRepository.save(prof);
                    return ResponseEntity.ok(toResponseDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cod}")
    public ResponseEntity<Void> deletar(@PathVariable Integer cod) {
        if (!professorRepository.existsById(cod))
            return ResponseEntity.notFound().build();
        else {
            professorRepository.deleteById(cod);
            return ResponseEntity.noContent().build();

        }

    }

}
