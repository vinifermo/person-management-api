package com.attornatus.backend.domain.service;

import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.attornatus.backend.domain.exception.CustomExceptionHandler.ENDERECO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public Endereco findById(UUID id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENDERECO_NAO_ENCONTRADO)));
    }

    @Transactional
    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Endereco findEnderecoPrincipal(UUID pessoaId) {

        return enderecoRepository
                .findFirstByPessoaIdAndEnderecoPrincipalIsTrue(pessoaId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENDERECO_NAO_ENCONTRADO)));
    }

}
