package com.joaoszczypiordev.sgci.dtos.endereco;

public record EnderecoResponse(
        String cep,
        String estado,
        String cidade,
        String rua,
        String bairro,
        Integer numero
) {}
