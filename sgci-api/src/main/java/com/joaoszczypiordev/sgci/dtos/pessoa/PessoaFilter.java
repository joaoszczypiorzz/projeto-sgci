package com.joaoszczypiordev.sgci.dtos.pessoa;

import com.joaoszczypiordev.sgci.dtos.pageable.FilterPageable;
import com.joaoszczypiordev.sgci.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFilter extends FilterPageable{

    private String nome;
    private TipoPessoa tipoPessoa;
    private String documento;
    private String cep;
    private String cidade;
    private String estado;
}
