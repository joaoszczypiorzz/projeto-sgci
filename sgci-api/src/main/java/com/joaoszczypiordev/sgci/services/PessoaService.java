package com.joaoszczypiordev.sgci.services;


import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoMapper;
import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoResponse;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaMapper;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaResponse;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaSaveDto;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaUpd;
import com.joaoszczypiordev.sgci.infra.exceptions.InvalidInputException;
import com.joaoszczypiordev.sgci.model.Endereco;
import com.joaoszczypiordev.sgci.model.Pessoa;
import com.joaoszczypiordev.sgci.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public Long updatePessoa(Long id, PessoaUpd pessoaUpd) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow();

        // Atualizando dados basicos da Pessoa:
        pessoa.setDocumento(pessoaUpd.documento());
        pessoa.setEstadoCivil(pessoaUpd.estadoCivil());
        pessoa.setNome(pessoaUpd.nome());
        pessoa.setProfissao(pessoaUpd.profissao());
        pessoa.setTipoPessoa(pessoaUpd.tipoPessoa());

        // Atualizando dados de endereço:
        pessoa.getEndereco().setCep(pessoaUpd.endereco().cep());
        pessoa.getEndereco().setEstado(pessoaUpd.endereco().estado());
        pessoa.getEndereco().setCidade(pessoaUpd.endereco().cidade());
        pessoa.getEndereco().setRua(pessoaUpd.endereco().rua());
        pessoa.getEndereco().setBairro(pessoaUpd.endereco().bairro());
        pessoa.getEndereco().setNumero(pessoaUpd.endereco().numero());

        return pessoaRepository.save(pessoa).getId();
    }

    @Transactional
    public void deletePessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow();

        pessoaRepository.delete(pessoa);
    }


    public List<PessoaResponse> findAll() {
        List<Pessoa> listPessoaBd = pessoaRepository.findAll();
        List<PessoaResponse> listResponse = new ArrayList<>();

        listPessoaBd.forEach(pessoa -> {
            EnderecoResponse enderecoResponse = EnderecoMapper.INSTANCE.toEnderecoResponse(pessoa.getEndereco());
            PessoaResponse pessoaResponse = PessoaMapper.INSTANCE.toPessoaResponse(pessoa, enderecoResponse);
            listResponse.add(pessoaResponse);
        });

        return listResponse;
    }
}
