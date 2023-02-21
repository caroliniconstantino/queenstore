package com.org.queenstore;

import com.org.queenstore.model.User;
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

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private com.org.queenstore.repository.UserRepository userRepository;

    @BeforeAll
    void start(){
        usuarioRepository.deleteAll();

        userService.cadastrarUsuario(new User(0l, "Rute", "rute@email.com", "sem foto", "abc"));
    }

    @Test
    @DisplayName("Cadastrar Um Usuário")
    public void deveCriarUmUsuario() {

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(new User(0L, "Paulo Antunes", "paulo_antunes@email.com.br", "13465278"));

        ResponseEntity<User> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, User.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getName(), corpoResposta.getBody().getName());
        assertEquals(corpoRequisicao.getBody().getUser(), corpoResposta.getBody().getUser());

    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {

        //no método abaixo, foi criado um novo usuario no banco de dados
        userService.cadastrarUser(new User(0L,
                "Maria da Silva", "maria_silva@email.com.br", "https://i.imgur.com/T12NIp9.jpg", "13465278"));

        //no método abaixo, foi criado um obj com novo usuario para ser usado no método da requisição abaixo
        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(new User(0L,
                "Maria da Silva", "maria_silva@email.com.br", "https://i.imgur.com/T12NIp9.jpg", "13465278"));

        //método abaixo é a requisição teste. Precisa passar a url, tipo da requisição, o corpo da requisição, o conteúdo esperado
        ResponseEntity<User> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, User.class);

        //no método abaixo, verifica se a resposta da requisição (Response), é a resposta esperada
        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario() {

        Optional<User> usuarioCadastrado = userService.cadastrarUsuario(new User(0L,
                "Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "adm"));

        User usuarioUpdate = new User(usuarioCadastrado.get().getId(),
                "Juliana Andrews Ramos", "juliana_ramos@email.com.br", "https://i.imgur.com/yDRVeK7.jpg" , "juliana123");

        HttpEntity<User> corpoRequisicao = new HttpEntity<User>(usuarioUpdate);

        ResponseEntity<User> corpoResposta = testRestTemplate
                .withBasicAuth("rute@email.com", "abc")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, User.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
    }

    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosUsuarios() {

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Sabrina Sanches", "sabrina_sanches@email.com.br", "https://i.imgur.com/5M2p5Wb.jpg", "sabrina123"));

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Ricardo Marques", "ricardo_marques@email.com.br", "https://i.imgur.com/Sk5SjWE.jpg", "ricardo123"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("rute@email.com", "abc")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

    }
}
