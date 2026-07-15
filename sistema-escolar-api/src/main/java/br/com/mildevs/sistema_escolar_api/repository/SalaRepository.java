package br.com.mildevs.sistema_escolar_api.repository;

import br.com.mildevs.sistema_escolar_api.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala,Integer> {
}
