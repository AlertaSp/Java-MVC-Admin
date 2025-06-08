package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {

    Optional<LeituraSensor> findTopBySensor_Corrego_IdOrderByDataHoraDesc(Long corregoId);
}
