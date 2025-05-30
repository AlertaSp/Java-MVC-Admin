package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.Corrego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorregoRepository extends JpaRepository<Corrego, Long> {
    boolean existsByNome(String nome);
}
