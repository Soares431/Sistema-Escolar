package br.com.mildevs.sistema_escolar_api.DTO.Turma;

public class TurmaResumoDTO {

    private Integer codTurma;
    private String nome;

    public TurmaResumoDTO(Integer codTurma, String nome) {
        this.codTurma = codTurma;
        this.nome = nome;
    }

    public Integer getCodTurma() {
        return codTurma;
    }

    public String getNome() {
        return nome;
    }
}
