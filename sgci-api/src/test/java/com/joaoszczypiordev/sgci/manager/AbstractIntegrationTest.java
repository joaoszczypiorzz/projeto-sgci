package com.joaoszczypiordev.sgci.manager;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@ActiveProfiles("test")
@SpringBootTest
public abstract class AbstractIntegrationTest {

    // Instância estática iniciada manualmente (Singleton)
    static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("banco_teste")
            .withUsername("usuario_teste")
            .withPassword("senha_teste");

    static {
        mysqlContainer.start(); // Inicia o container uma única vez
    }

    // Função que injeta as variaveis do BD de teste dinamicamente
    @DynamicPropertySource
    static void registrarPropriedades(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
}
