package com.attornatus.backend.domain.repository;

import com.attornatus.backend.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    Optional<Endereco> findFirstByPessoaIdAndEnderecoPrincipalIsTrue(UUID pessoaId);
}
