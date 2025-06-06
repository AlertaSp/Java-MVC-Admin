package com.alerta_sp.mvc_admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Usa o application-test.properties
class MvcAdminApplicationTests {

    @Test
    void contextLoads() {
        // Verifica apenas se o contexto Spring carrega com sucesso
    }
}
