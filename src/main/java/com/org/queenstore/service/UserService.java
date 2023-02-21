package com.org.queenstore.service;

import com.org.queenstore.model.User;
import com.org.queenstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> cadastrarUsuario(User user) {

        if (userRepository.findByUser(usuario.getUsuario()).isPresent())
            return Optional.empty();

        user.setPassword(criptografarSenha(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }

    public Optional<User> atualizarUsuario(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> buscaUsuario = userRepository.findByUser(user.getUser());

            if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != user.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            user.setPassword(criptografarSenha(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

    }

    public Optional<UserLogin> autenticarUser(Optional<UserLogin> usuarioLogin) {

        Optional<User> usuario = userRepository.findByUser(usuarioLogin.get().getUser());

        if (usuario.isPresent()) {

            if (compararSenhas(usuarioLogin.get().getPassword(), usuario.get().getPassword())) {
//utiliza-se usuarioLogin p pegar oq o usuario está digitando no login e usuario para pegar a info do banco
                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setName(usuario.get().getName());
                usuarioLogin.get().setEmail(usuario.get().getEmail());
                usuarioLogin.get().setPassword(usuario.get().getPassword());
                usuarioLogin.get().setRole(usuario.get().getRole());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario()));

                return usuarioLogin;

            }
        }

        return Optional.empty();

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBanco);

    }

    //------------------------------GERA O TOKEN------------------------------
    private String gerarBasicToken(String usuario, String senha) {

        String token = usuario + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));//modelo de criptografia
        return "Basic " + new String(tokenBase64);

    }
}
