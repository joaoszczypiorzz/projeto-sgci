package com.joaoszczypiordev.sgci.factory.entity;

import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoSaveDto;
import com.joaoszczypiordev.sgci.dtos.endereco.EnderecoUpd;
import com.joaoszczypiordev.sgci.model.Endereco;

/**
 * Classe Factory utilizada para atribuir os valores que serão usados nos testes automáticos
 * @author João Szczypior
 */
public class EnderecoFactory {

    public static Endereco getEndereco () {
        return new Endereco(
                null,
                "86086100",
                "PR",
                "Londrina",
                "Rua Guilhermina Lahmann",
                "Aquilles Sthengel",
                15);
    }

    public static EnderecoSaveDto getEnderecoSaveDto () {
        return new EnderecoSaveDto("86086100",
                "PR",
                "Londrina",
                "Rua Guilhermina Lahmann",
                "Aquilles Sthengel",
                15);
    }

    public static EnderecoUpd getEnderecoUpd () {
        return new EnderecoUpd(
                "77024562",
                "SP",
                "Araçatuba",
                "Av. 13 de Junho",
                "Nova Parati",
                150
        );
    }
}
