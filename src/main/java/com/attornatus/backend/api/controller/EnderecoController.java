package com.attornatus.backend.api.controller;

import com.attornatus.backend.api.assembler.EnderecoAssembler;
import com.attornatus.backend.api.dto.request.EnderecoRequestDTO;
import com.attornatus.backend.api.dto.response.EnderecoResponseDTO;
import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.attornatus.backend.api.controller.EnderecoController.ENDERECO_RESOURCE_PATH;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = ENDERECO_RESOURCE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class EnderecoController {
    public static final String ENDERECO_RESOURCE_PATH = "/endereco";
    public static final String ID_RESOURCE_PATH = "/{id}";
    public static final String PRINCIPAL_RESOURCE_PATH = ID_RESOURCE_PATH + "/endereco-principal";
    private final EnderecoService service;
    private final EnderecoAssembler enderecoAssembler;

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        List<Endereco> enderecos = service.findAll();

        return ResponseEntity.ok(enderecoAssembler.toCollectionModel(enderecos));
    }

    @GetMapping(value = ID_RESOURCE_PATH)
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable UUID id) {
        Endereco endereco = service.findById(id);

        return ResponseEntity.ok(enderecoAssembler.toModel(endereco));
    }

    @GetMapping(value = PRINCIPAL_RESOURCE_PATH)
    public ResponseEntity<EnderecoResponseDTO> findEnderecoPrincipal(@PathVariable UUID id) {

        Endereco enderecoPrincipal = service.findEnderecoPrincipal(id);

        return ResponseEntity.ok(enderecoAssembler.toModel(enderecoPrincipal));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = enderecoAssembler.toEntity(enderecoRequestDTO);

        service.save(endereco);

        URI location = enderecoAssembler.buildLocationUri(endereco);

        return ResponseEntity.created(location).build();
    }
}
