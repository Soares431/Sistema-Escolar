package br.com.mildevs.sistema_escolar_api.DTO.SalaDTO;

import br.com.mildevs.sistema_escolar_api.DTO.Turma.TurmaResumoDTO;

public class SalaResponseDTO {

    private Integer nroSala;
    private double largura;
    private double comprimento;
    private double altura;
    private TurmaResumoDTO turma;

    public SalaResponseDTO(Integer nroSala, double largura, double comprimento, double altura, TurmaResumoDTO turma) {
        this.nroSala = nroSala;
        this.largura = largura;
        this.comprimento = comprimento;
        this.altura = altura;
        this.turma = turma;
    }

    public Integer getNroSala() {
        return nroSala;
    }

    public double getLargura() {
        return largura;
    }

    public double getComprimento() {
        return comprimento;
    }

    public double getAltura() {
        return altura;
    }

    public TurmaResumoDTO getTurma() {
        return turma;
    }
}
