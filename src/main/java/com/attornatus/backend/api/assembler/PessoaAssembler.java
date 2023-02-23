package com.attornatus.backend.api.assembler;

import com.attornatus.backend.api.dto.request.PessoaRequestDTO;
import com.attornatus.backend.api.dto.response.EnderecoResponseDTO;
import com.attornatus.backend.api.dto.response.PessoaResponseDTO;
import com.attornatus.backend.domain.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class PessoaAssembler {

    private final EnderecoAssembler enderecoAssembler;

    public PessoaResponseDTO toModel(Pessoa pessoa) {
        List<EnderecoResponseDTO> enderecoResponseDTOS = enderecoAssembler.toCollectionModel(pessoa.getEndereco());

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setNome(pessoa.getNome());
        pessoaResponseDTO.setDataNascimento(pessoa.getDataNascimento());
        pessoaResponseDTO.setEndereco(enderecoResponseDTOS);

        return pessoaResponseDTO;
    }

    public List<PessoaResponseDTO> toCollectionModel(List<Pessoa> pessoas) {

        return pessoas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Pessoa toEntity(PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaRequestDTO.getNome());
        pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());

        return pessoa;
    }

    public URI buildLocationUri(Pessoa pessoa) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();
        log.info("Criado nova pessoa com id: {}", pessoa.getId());

        return location;
    }
}
