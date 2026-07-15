package br.com.mildevs.sistema_escolar_api.DTO.Turma;

import br.com.mildevs.sistema_escolar_api.DTO.AlunoDTO.AlunoResumoDTO;
import br.com.mildevs.sistema_escolar_api.DTO.ProfessorDTO.ProfessorResumoDTO;

import java.util.List;

public class TurmaResponseDTO {


    private Integer codTurma;
    private String nome;
    private ProfessorResumoDTO professor;
    private List<AlunoResumoDTO> aluno;

    public TurmaResponseDTO(Integer codTurma, String nome, ProfessorResumoDTO professor, List<AlunoResumoDTO> aluno) {
        this.codTurma = codTurma;
        this.nome = nome;
        this.professor = professor;
        this.aluno = aluno;
    }

    public Integer getCodTurma() {
        return codTurma;
    }

    public String getNome() {
        return nome;
    }

    public ProfessorResumoDTO getProfessor() {
        return professor;
    }

    public List<AlunoResumoDTO> getAluno() {
        return aluno;
    }

}
