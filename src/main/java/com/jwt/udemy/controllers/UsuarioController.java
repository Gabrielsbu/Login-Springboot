package com.jwt.udemy.controllers;

import com.jwt.udemy.domain.dto.CredenciaisDTO;
import com.jwt.udemy.domain.dto.TokenDTO;
import com.jwt.udemy.domain.entities.Usuario;
import com.jwt.udemy.domain.exceptions.SenhaInvalidaException;
import com.jwt.udemy.domain.jwt.JwtService;
import com.jwt.udemy.domain.service.Impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioService.salvar(usuario);

    }

    @PostMapping("/auth")
    public TokenDTO authenticar(@RequestBody CredenciaisDTO credenciaisDTO) {

        try{
           Usuario usuario = Usuario.builder()
                   .login(credenciaisDTO.getLogin())
                   .senha(credenciaisDTO.getSenha()).build();

           UserDetails usuarioAutenticado = usuarioService.authenticar(usuario);

           String token = jwtService.gerarToken(usuario);
           return new TokenDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }


}
