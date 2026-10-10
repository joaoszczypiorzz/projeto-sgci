package com.joaoszczypiordev.sgci.dtos.pessoa;

import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoSaveDto;
import com.joaoszczypiordev.sgci.enums.EstadoCivil;
import com.joaoszczypiordev.sgci.enums.TipoPessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaSaveDto(
   @NotBlank(message = "O nome é obrigatório.")
   String nome,
   @NotNull(message = "É obrigatório informar o tipo pessoa.")
   TipoPessoa tipoPessoa,
   @NotBlank(message = "É obrigatório informar o número do documento.")
   String documento,
   @NotBlank(message = "É obrigatório informar a profissão.")
   String profissao,
   @NotNull(message = "É obrigatório informar o estado civil.")
   EstadoCivil estadoCivil,
   EnderecoSaveDto endereco
) {}
