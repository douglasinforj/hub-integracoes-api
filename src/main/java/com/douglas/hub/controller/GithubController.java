package com.douglas.hub.controller;

import com.douglas.hub.client.GithubClient;
import com.douglas.hub.dto.github.RepositorioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubController {

    private final GithubClient githubClient;

    public GithubController(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @GetMapping("/usuarios/{usuario}/repos")
    public ResponseEntity<List<RepositorioDTO>> buscarRepositorios(
        @PathVariable("usuario") String usuario,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "per_page", defaultValue = "10") int per_page
    ) {
        List<RepositorioDTO> repos = githubClient.buscarRepositorios(usuario, page, per_page, "updated");
        return ResponseEntity.ok(repos);
    }

    @GetMapping("/usuarios/{usuario}")
    public ResponseEntity<Object> buscarPerfil(@PathVariable("usuario") String usuario) {
        return ResponseEntity.ok(githubClient.buscarPerfil(usuario));
    }
}