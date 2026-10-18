package com.joaoszczypiordev.sgci.factory.entity;

import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoSaveDto;
import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoUpd;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaFilter;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaSaveDto;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaUpd;
import com.joaoszczypiordev.sgci.enums.EstadoCivil;
import com.joaoszczypiordev.sgci.enums.TipoPessoa;
import com.joaoszczypiordev.sgci.model.Endereco;
import com.joaoszczypiordev.sgci.model.Pessoa;
import org.springframework.data.domain.Sort;

import java.util.Random;

/**
 * Classe Factory utilizada para atribuir os valores que serão usados nos testes automáticos
 * @author João Szczypior
 */
public class PessoaFactory {


    public static Pessoa getPessoa(Endereco endereco) {
        return new Pessoa(
                null,
                "Lorem Impsum",
                TipoPessoa.PESSOA_FISICA,
                gerarCpfAleatorio(),
                "Desenvolvedor Junior",
                EstadoCivil.SOLTEIRO,
                endereco
        );
    }


    public static PessoaSaveDto getPessoaSaveDto (EnderecoSaveDto enderecoSaveDto, String documento) {
        return new PessoaSaveDto(
                "Lorem Impsum",
                TipoPessoa.PESSOA_FISICA,
                "05038771076",
                "Desenvolvedor Junior",
                EstadoCivil.SOLTEIRO,
                enderecoSaveDto
        );
    }

    public static PessoaUpd getPessoaUpd (EnderecoUpd enderecoUpd) {
        return new PessoaUpd(
                "Lorem Impsum atualizado",
                TipoPessoa.PESSOA_JURIDICA,
                gerarCpfAleatorio(),
                "Desenvolvedor Senior Google",
                EstadoCivil.SEPARADO,
                enderecoUpd
        );

    }

    public static PessoaFilter getPessoaFilter (String cep) {
        PessoaFilter filtros = new PessoaFilter();
        filtros.setPage(0);
        filtros.setSize(1);
        filtros.setDirection(Sort.Direction.ASC);
        filtros.setOrderBy("nome");
        filtros.setCep(cep);
        return filtros;
    }

    private static String gerarCpfAleatorio() {
        Random random = new Random();
        int numeroNF = (random.nextInt(90000000) + 10000000 );
        return String.valueOf(numeroNF);
    }

}
