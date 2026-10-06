package com.joaoszczypiordev.sgci.dtos.pessoa;

import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoSaveDto;
import com.joaoszczypiordev.sgci.enums.EstadoCivil;
import com.joaoszczypiordev.sgci.enums.TipoPessoa;
import jakarta.validation.constraints.NotBlank;

public record PessoaSaveDto(
   @NotBlank(message = "O nome é obrigatório.")
   String nome,
   @NotBlank(message = "É obrigatório informar o tipo pessoa.")
   TipoPessoa tipoPessoa,
   @NotBlank(message = "É obrigatório informar o número do documento.")
   String documento,
   @NotBlank(message = "É obrigatório informar a profissão.")
   String profissao,
   @NotBlank(message = "É obrigatório informar o estado civil.")
   EstadoCivil estadoCivil,
   EnderecoSaveDto endereco
) {}
