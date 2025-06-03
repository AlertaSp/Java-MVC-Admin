package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.Alerta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    @Query("SELECT a FROM Alerta a ORDER BY a.dataHora DESC")
    List<Alerta> buscarUltimosAlertas(Pageable pageable);

}
