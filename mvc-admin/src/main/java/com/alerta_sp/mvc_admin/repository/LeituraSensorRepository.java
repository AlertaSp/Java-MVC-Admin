package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {

    Optional<LeituraSensor> findTopBySensor_Corrego_IdOrderByDataHoraDesc(Long corregoId);

    // Retorna as 24 leituras mais recentes de um determinado córrego
    List<LeituraSensor> findTop24BySensor_Corrego_IdOrderByDataHoraDesc(Long corregoId);
}
