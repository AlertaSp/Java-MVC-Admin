package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    boolean existsByCodigo(String codigo);
}
