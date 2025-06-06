package com.alerta_sp.mvc_admin.repository;

import com.alerta_sp.mvc_admin.model.Corrego;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CorregoRepositoryTest {

    @Autowired
    private CorregoRepository repository;

    @Test
    void whenSave_thenFindByNomeWorks() {
        Corrego c = new Corrego();
        c.setNome("Pinheiros");
        c.setLatitude("-23.56");
        c.setLongitude("-46.67");
        c.setNivelAlerta(2.0);
        c.setNivelCritico(3.0);
        repository.save(c);

        assertTrue(repository.existsByNome("Pinheiros"));
        Optional<Corrego> opt = repository.findByNome("Pinheiros");
        assertTrue(opt.isPresent());
        assertEquals("Pinheiros", opt.get().getNome());
    }
}
