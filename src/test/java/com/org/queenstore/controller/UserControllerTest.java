package com.org.queenstore.controller;

import com.org.queenstore.enums.Role;
import com.org.queenstore.model.User;
import com.org.queenstore.repository.UserRepository;
import com.org.queenstore.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @BeforeAll
    void start(){
        userRepository.deleteAll();

        userService.signup(new User(0l, "Rute", "rute@email.com", "abc", Role.CLIENT, "293829382", "Av Brasil 1000", "Colcci"));
    }

    @Test
    @DisplayName("Cadastrar Um Usuário")
    public void deveCriarUmUsuario() {

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(new User(0l, "Novo usuário", "newuser@email.com", "abc", Role.SELLER, "293829382", "Av Brasil 1000", "Zara"));

        ResponseEntity<User> corpoResposta = testRestTemplate
                .exchange("/users/logon", HttpMethod.POST, corpoRequisicao, User.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getName(), corpoResposta.getBody().getName());
        assertEquals(corpoRequisicao.getBody().getEmail(), corpoResposta.getBody().getEmail());

    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {

        userService.signup(new User(0l, "Rute", "rute@email.com", "abc", Role.CLIENT, "293829382", "Av Brasil 1000", "Adidas"));

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(new User(0l, "Rute", "rute@email.com", "abc", Role.CLIENT, "293829382", "Av Brasil 1000", "Adidas"));

        ResponseEntity<User> corpoResposta = testRestTemplate
                .exchange("/users/logon", HttpMethod.POST, corpoRequisicao, User.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario() {

        Optional<User> usuarioCadastrado = userService.signup(new User(0l, "Rute", "rute@email.com", "abc", Role.SELLER, "293829382", "Av Brasil 1000", "Colcci"));

        User usuarioUpdate = new User(usuarioCadastrado.get().getId(),
        "Nova Rute", "rute@email.com", "abc", Role.CLIENT, "293829382", "Av Brasil 1000", "Colcci");

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(usuarioUpdate);

        ResponseEntity<User> corpoResposta = testRestTemplate
                .withBasicAuth("rute@email.com", "abc")
                .exchange("/users/update", HttpMethod.PUT, corpoRequisicao, User.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getName(), corpoResposta.getBody().getName());
        assertEquals(corpoRequisicao.getBody().getEmail(), corpoResposta.getBody().getEmail());
    }

    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosUsuarios() {

        userService.signup(new User(0l, "Rute", "rute@email.com", "abc", Role.CLIENT, "293829382", "Av Brasil 1000", "Colcci"));

        userService.signup(new User(0L,
                "Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", Role.SELLER, "293829382", "Av Brasil 1000", "Colcci"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("rute@email.com", "abc")
                .exchange("/users/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

    }
}
