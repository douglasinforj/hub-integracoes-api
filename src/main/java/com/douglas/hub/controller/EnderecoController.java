package com.douglas.hub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.hub.client.ViaCepClient;
import com.douglas.hub.dto.EnderecoDTO;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final ViaCepClient viaCepClient;   //Injeta ViaCliente (dados da api externa)

    public EnderecoController(ViaCepClient viaCepClient) {   // DI - Via construtor
        this.viaCepClient = viaCepClient;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoDTO>buscarEndereco(@PathVariable("cep") String cep) {
        EnderecoDTO endereco = viaCepClient.buscarEndereco(cep);
        return ResponseEntity.ok(endereco);
    }
}
