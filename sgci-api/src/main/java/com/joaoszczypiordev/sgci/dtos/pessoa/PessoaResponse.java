package com.joaoszczypiordev.sgci.dtos.pessoa;

import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoResponse;
import com.joaoszczypiordev.sgci.enums.EstadoCivil;
import com.joaoszczypiordev.sgci.enums.TipoPessoa;

public record PessoaResponse(
        String nome,
        TipoPessoa tipoPessoa,
        String documento,
        String profissao,
        EstadoCivil estadoCivil,
        EnderecoResponse endereco
) {}
