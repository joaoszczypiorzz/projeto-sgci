package com.joaoszczypiordev.sgci.controller;

import com.joaoszczypiordev.sgci.dtos.pageable.ResponsePagedCommom;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaFilter;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaResponse;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaSaveDto;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaUpd;
import com.joaoszczypiordev.sgci.services.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping("/create")
    public ResponseEntity<Long> createPessoa(@Valid @RequestBody PessoaSaveDto pessoaSaveDto) {
        Long id = pessoaService.createPessoa(pessoaSaveDto);
        return ResponseEntity.ok(id);
    }


    @DeleteMapping(path = "{idPessoa}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long idPessoa) {
        pessoaService.deletePessoa(idPessoa);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update/{idPessoa}")
    public ResponseEntity<Long> updatePessoa(@Valid @PathVariable(required = true) Long idPessoa, @RequestBody PessoaUpd pessoaUpd) {
        Long id = pessoaService.updatePessoa(idPessoa, pessoaUpd);
        return ResponseEntity.ok(id);
    }

    @GetMapping(path = "{idPessoa}")
    public ResponseEntity<PessoaResponse> findById (@PathVariable Long idPessoa) {
        return ResponseEntity.ok(pessoaService.findById(idPessoa));
    }

    @GetMapping
    public ResponseEntity<ResponsePagedCommom<PessoaResponse>> findAll (@Valid PessoaFilter filtros) {
        return ResponseEntity.ok(pessoaService.findAll(filtros));
    }

    @GetMapping("/exportar-csv")
    public ResponseEntity<String> exportarCsv () {
        return ResponseEntity.ok(pessoaService.exportarParaBase64());
    }
}
