package br.com.mildevs.sistema_escolar_api.DTO.Turma;

import br.com.mildevs.sistema_escolar_api.DTO.AlunoDTO.AlunoResumoDTO;
import br.com.mildevs.sistema_escolar_api.DTO.ProfessorDTO.ProfessorResumoDTO;

import java.util.List;

public class TurmaRequestDTO {

   private String nome;
   private Integer codProfessor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodProfessor() {
        return codProfessor;
    }

    public void setCodProfessor(Integer codProfessor) {
        this.codProfessor = codProfessor;
    }
}
