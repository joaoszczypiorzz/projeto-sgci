package com.joaoszczypiordev.sgci.services;


import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoMapper;
import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoResponse;
import com.joaoszczypiordev.sgci.dtos.pageable.ResponsePagedCommom;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaFilter;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaMapper;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaResponse;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaSaveDto;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaUpd;
import com.joaoszczypiordev.sgci.infra.exceptions.InvalidInputException;
import com.joaoszczypiordev.sgci.model.Endereco;
import com.joaoszczypiordev.sgci.model.Pessoa;
import com.joaoszczypiordev.sgci.repository.PessoaRepository;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Base64;
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

    @Transactional(readOnly = true)
    public PessoaResponse findById (Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow();

        EnderecoResponse enderecoResponse = EnderecoMapper.INSTANCE.toEnderecoResponse(pessoa.getEndereco());
        return PessoaMapper.INSTANCE.toPessoaResponse(pessoa, enderecoResponse);
    }

    /**
     * Função que realiza Paginação dos dados, utilizando objeto Padrão {@link org.springframework.data.domain.Pageable} do SpringBoot
     * Assim como também realiza filtros dinamicos nos dados Utilizando os padrões de {@link Specification} do Framework
     * @param filtros Objeto contento os filtros para Classe Pessoa e filtros para o Pageable
     * @return Retorna uma Classe padronizada de response
     * @Author João Szczypior
     */
    public ResponsePagedCommom<PessoaResponse> findAll(PessoaFilter filtros) {

        // Filtros dinamicos:
        Specification<Pessoa> CustomFilters = (root, query, cb) -> {
            List<Predicate> condicoes = new ArrayList<>();

            if (filtros.getNome() != null) {
                condicoes.add(cb.like(root.get("nome"), "%" + filtros.getNome() + "%"));
            }

            if(filtros.getDocumento() != null) {
                condicoes.add(cb.equal(root.get("documento"), filtros.getDocumento()));
            }

            if(filtros.getCep() != null) {
                condicoes.add(cb.equal(root.get("endereco").get("cep"), filtros.getCep()));
            }

            if(filtros.getCidade() != null) {
                condicoes.add(cb.equal(root.get("endereco").get("cidade"), filtros.getCidade()));
            }

            if(filtros.getEstado() != null) {
                condicoes.add(cb.equal(root.get("endereco").get("estado"), filtros.getEstado()));
            }

            if(filtros.getTipoPessoa() != null) {
                condicoes.add(cb.equal(root.get("tipoPessoa"), filtros.getTipoPessoa()));
            }

            return cb.and(condicoes.toArray(Predicate[]::new));
        };

        Page<Pessoa> listPessoaBd = pessoaRepository.findAll(CustomFilters, PageRequest.of(filtros.getPage(), filtros.getSize(), Sort.by(filtros.getDirection(), filtros.getOrderBy())));
        List<PessoaResponse> listResponse = new ArrayList<>();

        listPessoaBd.forEach(pessoa -> {
            EnderecoResponse enderecoResponse = EnderecoMapper.INSTANCE.toEnderecoResponse(pessoa.getEndereco());
            PessoaResponse pessoaResponse = PessoaMapper.INSTANCE.toPessoaResponse(pessoa, enderecoResponse);
            listResponse.add(pessoaResponse);
        });

        return new ResponsePagedCommom<PessoaResponse>(
                listResponse,
                listPessoaBd.getTotalElements(),
                listPessoaBd.getTotalPages(),
                filtros.getSize()
        );
    }

    public String exportarParaBase64 () {
        List<Pessoa> listPessoaBd = pessoaRepository.findAll();

        try (
        // Objeto para armazenar em memoria o arquivo
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Objeto que possibilita conversão de dados para escrita no arquivo OUT
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        CSVWriter csvWriter = new CSVWriter(writer,
                ICSVWriter.DEFAULT_SEPARATOR, // Separador de campos no CSV (por padrão, vírgula)
                ICSVWriter.DEFAULT_QUOTE_CHARACTER, // Nenhum caractere de citação será usado
                ICSVWriter.DEFAULT_ESCAPE_CHARACTER, // Caractere de escape padrão
                ICSVWriter.DEFAULT_LINE_END);  // Final de Linha padrão (por exemplo, "\r\n")
        ) {
            // Definindo cabeçalho do arquivo
            String[] header = {"Nome", "Documento", "Profissao", "Estado Civil"};
            csvWriter.writeNext(header);

            // Para cada pessoa encontrada escrever 1 linha no arquivo com suas informações
            listPessoaBd.forEach(pessoa -> {
                String[] data = {pessoa.getNome(), pessoa.getDocumento(), pessoa.getProfissao(), pessoa.getEstadoCivil().toString()};
                csvWriter.writeNext(data);
            });

            // Garante que tudo será escrito na ordem definida pela sequencia do meu codigo
            writer.flush();

            // Retornando o arquivo de outPut base64 convertido para uma String
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erro inesperado ao exportar arquivo para Base64", e);
        }


    }
}
