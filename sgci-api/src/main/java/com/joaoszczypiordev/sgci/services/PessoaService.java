package com.joaoszczypiordev.sgci.services;


import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaSaveDto;
import com.joaoszczypiordev.sgci.infra.exceptions.InvalidInputException;
import com.joaoszczypiordev.sgci.model.Endereco;
import com.joaoszczypiordev.sgci.model.Pessoa;
import com.joaoszczypiordev.sgci.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;


    @Transactional
    public Long createPessoa(PessoaSaveDto pessoaSaveDto) {
        if(pessoaRepository.existsByDocumento(pessoaSaveDto.documento())) {
            throw new InvalidInputException("Documento informado já cadastrado, favor informar outro valor.");
        }

        Endereco endereco = new Endereco(
                null,
                pessoaSaveDto.endereco().cep(),
                pessoaSaveDto.endereco().estado(),
                pessoaSaveDto.endereco().cidade(),
                pessoaSaveDto.endereco().rua(),
                pessoaSaveDto.endereco().bairro(),
                pessoaSaveDto.endereco().numero()
        );

        Pessoa pessoa = new Pessoa(
                null,
                pessoaSaveDto.nome(),
                pessoaSaveDto.tipoPessoa(),
                pessoaSaveDto.documento(),
                pessoaSaveDto.profissao(),
                pessoaSaveDto.estadoCivil(),
                endereco
        );

        return pessoaRepository.save(pessoa).getId();
    }
}
