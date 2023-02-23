package com.attornatus.backend.domain.service;

import com.attornatus.backend.domain.model.Pessoa;
import com.attornatus.backend.domain.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.attornatus.backend.domain.exception.CustomExceptionHandler.PESSOA_NAO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;


    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findById(UUID id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PESSOA_NAO_ENCONTRADA)));
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa update(UUID id, Pessoa peessoa) {
        Pessoa pessoSalva = findById(id);
        BeanUtils.copyProperties(peessoa, pessoSalva, "id");

        return pessoaRepository.save(pessoSalva);
    }
}
