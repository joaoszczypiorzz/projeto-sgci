package com.joaoszczypiordev.sgci.dtos.endereco;

import com.joaoszczypiordev.sgci.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EnderecoMapper {
    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    EnderecoResponse toEnderecoResponse(Endereco endereco);
}
