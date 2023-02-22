package com.org.queenstore.repository;

import com.org.queenstore.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void start(){
        userRepository.deleteAll();

        userRepository.save(new User(0L, "João da Silva", "joao@email.com.br", "13465278", "cliente", "293829382", "Av Brasil 1000", "", "Colcci"));

        userRepository.save(new User(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", "cliente", "293829382", "Av Brasil 1000", "", "Colcci"));

        userRepository.save(new User(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", "vendedor", "293829382", "Av Brasil 1000", "", "Colcci"));

        userRepository.save(new User(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "vendedor", "293829382", "Av Brasil 1000", "", "Colcci"));
    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornarUmUsuario() {

        Optional<User> usuario = userRepository.findByUser("joao@email.com.br");

        assertTrue(usuario.get().getEmail().equals("joao@email.com.br"));
    }

    @Test
    @DisplayName("Retorna 3 usuarios")
    public void deveRetornarTresUsuarios() {

        List<User> listaDeUsuarios = userRepository.findAllByNameContainingIgnoreCase("Silva");

        assertEquals(3, listaDeUsuarios.size());

        assertTrue(listaDeUsuarios.get(0).getName().equals("João da Silva"));
        assertTrue(listaDeUsuarios.get(1).getName().equals("Manuela da Silva"));
        assertTrue(listaDeUsuarios.get(2).getName().equals("Adriana da Silva"));

    }


    @AfterAll
    public void end() {
        userRepository.deleteAll();
    }
}
