package com.org.queenstore.service;

import com.org.queenstore.model.User;
import com.org.queenstore.model.UserLogin;
import com.org.queenstore.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //cadastrar usuário
    public Optional<User> signup(User user) {

        if (userRepository.findByUser(user.getEmail()).isPresent())
            return Optional.empty();

        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }

    //atualiza usuário
    public Optional<User> updateUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> getUser = userRepository.findByUser(user.getEmail());

            if ( (getUser.isPresent()) && ( getUser.get().getId() != user.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            user.setPassword(encryptPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

    }

    //autentica
    public Optional<UserLogin> authenticator(Optional<UserLogin> usuarioLogin) {

        Optional<User> usuario = userRepository.findByUser(usuarioLogin.get().getEmail());

        if (usuario.isPresent()) {

            if (compararPassword(usuarioLogin.get().getPassword(), usuario.get().getPassword())) {

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setName(usuario.get().getName());
                usuarioLogin.get().setEmail(usuario.get().getEmail());
                usuarioLogin.get().setPassword(usuario.get().getPassword());
                usuarioLogin.get().setRole(usuario.get().getRole());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getEmail(), usuarioLogin.get().getPassword()));

                return usuarioLogin;

            }
        }

        return Optional.empty();

    }

    //criptografa a senha
    private String encryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);

    }

    private boolean compararPassword(String senhaDigitada, String senhaBanco) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBanco);

    }


    private String gerarBasicToken(String usuario, String senha) {

        String token = usuario + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));//modelo de criptografia
        return "Basic " + new String(tokenBase64);

    }
}
