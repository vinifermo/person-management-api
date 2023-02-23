package com.attornatus.backend.api.assembler;

import com.attornatus.backend.api.dto.request.EnderecoRequestDTO;
import com.attornatus.backend.api.dto.response.EnderecoResponseDTO;
import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class EnderecoAssembler {

    private final ModelMapper modelMapper;

    public EnderecoResponseDTO toModel(Endereco endereco) {

        return modelMapper.map(endereco, EnderecoResponseDTO.class);
    }

    public List<EnderecoResponseDTO> toCollectionModel(List<Endereco> endereco) {

        return endereco.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Endereco toEntity(EnderecoRequestDTO enderecoResponseDTO) {
        return modelMapper.map(enderecoResponseDTO, Endereco.class);
    }

    public URI buildLocationUri(Endereco endereco) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(endereco.getId())
                .toUri();
        log.info("Criado novo endereço com id: {}", endereco.getId());

        return location;
    }

    public List<Endereco> toEntity(List<EnderecoRequestDTO> enderecoRequest, Pessoa pessoa) {

        return enderecoRequest.stream().map(enderecoRequestDTO -> {
            Endereco endereco = new Endereco();

            endereco.setLogradouro(enderecoRequestDTO.getLogradouro());
            endereco.setCep(enderecoRequestDTO.getCep());
            endereco.setNumero(enderecoRequestDTO.getNumero());
            endereco.setCidade(enderecoRequestDTO.getCidade());
            endereco.setEnderecoPrincipal(enderecoRequestDTO.isEnderecoPrincipal());
            endereco.setPessoa(pessoa);

            return endereco;
        }).collect(Collectors.toList());
    }
}
