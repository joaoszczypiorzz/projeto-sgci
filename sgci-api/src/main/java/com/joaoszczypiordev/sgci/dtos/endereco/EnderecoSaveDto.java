package com.joaoszczypiordev.sgci.dtos.endereco;

import jakarta.validation.constraints.NotBlank;

public record EnderecoSaveDto(
   String cep,
   @NotBlank(message = "Estado é obrigatório.")
   String estado,
   @NotBlank(message = "Cidade é obrigatório.")
   String cidade,
   String rua,
   String bairro,
   Integer numero
) {}
