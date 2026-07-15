package br.com.mildevs.sistema_escolar_api.repository;

import br.com.mildevs.sistema_escolar_api.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

}
