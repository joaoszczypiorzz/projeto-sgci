package com.joaoszczypiordev.sgci.repository;

import com.joaoszczypiordev.sgci.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByDocumento(String documento);
}
