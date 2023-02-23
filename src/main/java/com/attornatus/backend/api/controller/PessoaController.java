package com.attornatus.backend.api.controller;

import com.attornatus.backend.api.assembler.EnderecoAssembler;
import com.attornatus.backend.api.assembler.PessoaAssembler;
import com.attornatus.backend.api.dto.request.PessoaRequestDTO;
import com.attornatus.backend.api.dto.response.PessoaResponseDTO;
import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.model.Pessoa;
import com.attornatus.backend.domain.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.attornatus.backend.api.controller.PessoaController.PESSOA_RESOURCE_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = PESSOA_RESOURCE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {

    public static final String PESSOA_RESOURCE_PATH = "/pessoa";
    public static final String ID_RESOURCE_PATH = "/{id}";
    private final PessoaService service;
    private final PessoaAssembler pessoaAssembler;
    private final EnderecoAssembler enderecoAssembler;

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> findAll() {
        List<Pessoa> pessoas = service.findAll();

        return ResponseEntity.ok(pessoaAssembler.toCollectionModel(pessoas));
    }

    @GetMapping(value = ID_RESOURCE_PATH)
    public ResponseEntity<PessoaResponseDTO> findById(@PathVariable UUID id) {
        Pessoa pessoa = service.findById(id);

        return ResponseEntity.ok(pessoaAssembler.toModel(pessoa));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = pessoaAssembler.toEntity(pessoaRequestDTO);
        List<Endereco> enderecos = enderecoAssembler.toEntity(pessoaRequestDTO.getEndereco(), pessoa);

        pessoa.setEndereco(enderecos);
        pessoa = service.save(pessoa);

        return ResponseEntity.created(pessoaAssembler.buildLocationUri(pessoa)).build();
    }

    @PutMapping(value = ID_RESOURCE_PATH)
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = pessoaAssembler.toEntity(pessoaRequestDTO);
        pessoa = service.update(id, pessoa);

        return ResponseEntity.noContent().build();
    }
}