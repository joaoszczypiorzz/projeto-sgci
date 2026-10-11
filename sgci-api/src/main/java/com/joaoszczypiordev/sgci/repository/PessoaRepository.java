package com.joaoszczypiordev.sgci.repository;

import com.joaoszczypiordev.sgci.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByDocumento(String documento);


    /**
     * Esta função com a anotação {@link @EntityGraph} diz ao Hibernate quando for realizar esse FindAll buscar os Atributos da tabela endereço
     * Já no select principal evitando N+1 querie, pois o Hibernate vai buscar e Refreshar na memoria todos os
     * Atributos tanto da classe pessoa Quanto da classe de Endereço em apenas 1 SELECT
     * @param filters filtros de Busca
     * @param pageable paginação dos registros
     * @return Um objeto Paginado
     * @Author João Szczypior
     */
    @EntityGraph(attributePaths = {"endereco"})
    Page<Pessoa> findAll(Specification<Pessoa> filters, Pageable pageable);
}
