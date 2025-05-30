package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {
    List<LeituraSensor> findBySensorId(Long sensorId);
}
