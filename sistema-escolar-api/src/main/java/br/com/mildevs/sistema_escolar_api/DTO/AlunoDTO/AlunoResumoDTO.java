package br.com.mildevs.sistema_escolar_api.DTO.AlunoDTO;

public class AlunoResumoDTO {

    private Integer matricula;
    private String nome;

    public AlunoResumoDTO(Integer matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }
}
