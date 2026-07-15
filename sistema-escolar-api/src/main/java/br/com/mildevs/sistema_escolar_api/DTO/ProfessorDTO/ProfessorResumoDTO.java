package br.com.mildevs.sistema_escolar_api.DTO.ProfessorDTO;

public class ProfessorResumoDTO {

    private Integer codProfessor;
    private String nome;

    public ProfessorResumoDTO(Integer codProfessor, String nome) {
        this.codProfessor = codProfessor;
        this.nome = nome;
    }

    public Integer getCodProfessor() {
        return codProfessor;
    }

    public String getNome() {
        return nome;
    }
}
