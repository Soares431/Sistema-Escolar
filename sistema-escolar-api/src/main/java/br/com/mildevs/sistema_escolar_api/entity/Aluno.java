package br.com.mildevs.sistema_escolar_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "aluno")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matricula;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String serie;

    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataNascimento;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "turmas_alunos",
            joinColumns = @JoinColumn(name = "aluno_fk", referencedColumnName = "matricula"),
            inverseJoinColumns = @JoinColumn(name = "turma_fk", referencedColumnName = "cod_Turma")
    )
    @JsonIgnore
    private List<Turma> turmas;

    public Aluno() {

    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

}
