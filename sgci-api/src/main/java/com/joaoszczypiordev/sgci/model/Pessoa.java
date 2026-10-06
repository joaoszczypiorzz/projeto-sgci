package com.joaoszczypiordev.sgci.model;


import com.joaoszczypiordev.sgci.enums.EstadoCivil;
import com.joaoszczypiordev.sgci.enums.TipoPessoa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "en_tipo", nullable = false)
    private TipoPessoa tipoPessoa;

    @Column(name = "documento", nullable = false, unique = true)
    private String documento;

    @Column(name = "tx_profissao", nullable = false)
    private String profissao;

    @Enumerated(EnumType.STRING)
    @Column(name = "en_estado_civil", nullable = false)
    private EstadoCivil estadoCivil;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

}
