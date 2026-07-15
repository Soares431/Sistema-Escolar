package br.com.mildevs.sistema_escolar_api.DTO.AlunoDTO;

import java.time.LocalDate;

public class AlunoResponseDTO {

    private Integer matricula;
    private String nome;
    private String serie;
    private LocalDate dataNascimento;

    public AlunoResponseDTO(Integer matricula, String nome, String serie, LocalDate dataNascimento) {
        this.matricula = matricula;
        this.nome = nome;
        this.serie = serie;
        this.dataNascimento = dataNascimento;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getSerie() {
        return serie;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
