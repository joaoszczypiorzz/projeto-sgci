package com.joaoszczypiordev.sgci.manager;


import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoSaveDto;
import com.joaoszczypiordev.sgci.dtos.pageable.ResponsePagedCommom;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaResponse;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaSaveDto;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaUpd;
import com.joaoszczypiordev.sgci.factory.entity.EnderecoFactory;
import com.joaoszczypiordev.sgci.factory.entity.PessoaFactory;
import com.joaoszczypiordev.sgci.model.Pessoa;
import com.joaoszczypiordev.sgci.repository.PessoaRepository;
import com.joaoszczypiordev.sgci.services.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class TestPessoaService extends AbstractIntegrationTest {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PessoaRepository pessoaRepository;

    Pessoa pessoa = null;
    @BeforeEach
    void setUp () {
        pessoa = pessoaRepository.save(PessoaFactory.getPessoa(EnderecoFactory.getEndereco()));
    }


    @Test
    void Create_Pessoa () {

        EnderecoSaveDto enderecoSaveDto = EnderecoFactory.getEnderecoSaveDto();
        PessoaSaveDto pessoaSaveDto = PessoaFactory.getPessoaSaveDto(enderecoSaveDto, "79464233060");

        // Chamando meu service para executar o Create Pessoa
        Long pessoaId = pessoaService.createPessoa(pessoaSaveDto);

        // Este assert me garante que o meu metodo está criando de fato uma pessoa
        assertTrue(pessoaId != null && pessoaId > 0);
    }

    @Test
    void find_pessoa_ById () {
        PessoaResponse pessoaResponse = pessoaService.findById(pessoa.getId());

        // Valida se a pessoa response está sendo devolvida e se o seu documento é igual ao informado no dto de save
        assertTrue(pessoaResponse != null && pessoaResponse.documento().equals(pessoa.getDocumento()));
    }

    @Test
    void Find_All_Paged () {
        ResponsePagedCommom<PessoaResponse> retorno = pessoaService.findAll(PessoaFactory.getPessoaFilter(pessoa.getEndereco().getCep()));

        assertTrue(!retorno.getData().isEmpty() && retorno.getTotalPages() > 0);
    }

    @Test
    void Delete_Pessoa_ById () {
        pessoaService.deletePessoa(pessoa.getId());

        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoa.getId());
        assertTrue(pessoaOpt.isEmpty());
    }

    @Test
    void Update_Pessoa_ById () {
        PessoaUpd newData = PessoaFactory.getPessoaUpd(EnderecoFactory.getEnderecoUpd());

        Long pessoaAttId = pessoaService.updatePessoa(pessoa.getId(), newData);

        assertNotEquals(pessoa.getNome(), newData.nome());
        assertNotEquals(pessoa.getDocumento(), newData.documento());
    }
}
