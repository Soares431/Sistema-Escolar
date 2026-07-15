package br.com.mildevs.sistema_escolar_api.DTO.ProfessorDTO;

import java.math.BigDecimal;

public class ProfessorResponseDTO {

    private Integer codProfessor;
    private String nome;
    private String telefone;
    private String nivelGraduacao;
    private BigDecimal salario;
    private String disciplina;


    public ProfessorResponseDTO(Integer codProfessor, String nome, String telefone, String nivelGraduacao, BigDecimal salario, String disciplina) {
        this.codProfessor = codProfessor;
        this.nome = nome;
        this.telefone = telefone;
        this.nivelGraduacao = nivelGraduacao;
        this.salario = salario;
        this.disciplina = disciplina;
    }

    public Integer getCodProfessor() {
        return codProfessor;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNivelGraduacao() {
        return nivelGraduacao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getDisciplina() {
        return disciplina;
    }
}
