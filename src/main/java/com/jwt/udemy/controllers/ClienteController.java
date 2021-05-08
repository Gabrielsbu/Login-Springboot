package com.jwt.udemy.controllers;

import com.jwt.udemy.domain.entities.Cliente;
import com.jwt.udemy.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/cliente-create")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente client = clienteRepository.save(cliente);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/search-id/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        Cliente client = clienteRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return client;
    }

    @DeleteMapping("/delete-cliente/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        Optional<Cliente> clienteExistetente = clienteRepository.findById(id);

        if(!clienteExistetente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        clienteRepository.delete(clienteExistetente.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar-cliente/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if(!clienteExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteExistente.get().getId());

        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Cliente>> buscarAll(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        List<Cliente> clientesExistentes = clienteRepository.findAll(example);
        return ResponseEntity.ok(clientesExistentes);
    }
}
