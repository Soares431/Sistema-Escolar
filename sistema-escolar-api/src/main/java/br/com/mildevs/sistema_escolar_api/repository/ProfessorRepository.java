package br.com.mildevs.sistema_escolar_api.repository;

import br.com.mildevs.sistema_escolar_api.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor,Integer> {
}
