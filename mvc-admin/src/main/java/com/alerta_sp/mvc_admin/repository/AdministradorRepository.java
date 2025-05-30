package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Administrador findByUsername(String username);
    boolean existsByUsername(String username);
}
