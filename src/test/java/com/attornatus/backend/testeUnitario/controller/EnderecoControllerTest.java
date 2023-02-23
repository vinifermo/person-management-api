package com.attornatus.backend.testeUnitario.controller;

import com.attornatus.backend.api.assembler.EnderecoAssembler;
import com.attornatus.backend.api.assembler.PessoaAssembler;
import com.attornatus.backend.api.controller.EnderecoController;
import com.attornatus.backend.api.controller.PessoaController;
import com.attornatus.backend.api.dto.request.EnderecoRequestDTO;
import com.attornatus.backend.api.dto.response.EnderecoResponseDTO;
import com.attornatus.backend.api.dto.response.PessoaResponseDTO;
import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.model.Pessoa;
import com.attornatus.backend.domain.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.webservices.server.AutoConfigureMockWebServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class EnderecoControllerTest {

    private EnderecoController controller;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private EnderecoAssembler enderecoAssembler;

    @Mock
    private RequestAttributes attrs;

    private Endereco endereco = new Endereco();
    private EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
    private EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
    private Pessoa pessoa = new Pessoa();

    private void startEndereco() {
        endereco = new Endereco(UUID.fromString("7038b81b-f538-43f5-8c5e-537ee2ffdc46"), "Recreio", "8670-065", 10, "Rio de Janeiro/RJ",true,pessoa);
        enderecoResponseDTO = new EnderecoResponseDTO("7038b81b-f538-43f5-8c5e-537ee2ffdc46", "Recreio", "8670-065", 10, "Rio de Janeiro/RJ",true);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEndereco();
        RequestContextHolder.setRequestAttributes(attrs);
        this.controller = new EnderecoController(enderecoService,enderecoAssembler);
    }

    @Test
    void whenFindAllThenReturnAListOfEnderecos() {
        when(enderecoAssembler.toCollectionModel(List.of(endereco))).thenReturn(List.of(enderecoResponseDTO));
        when(enderecoService.findAll()).thenReturn(List.of(endereco));

        ResponseEntity<List<EnderecoResponseDTO>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(EnderecoResponseDTO.class, response.getBody().get(0).getClass());

        assertEquals("7038b81b-f538-43f5-8c5e-537ee2ffdc46",response.getBody().get(0).getId());
        assertEquals(10, response.getBody().get(0).getNumero());
        assertEquals("Recreio", response.getBody().get(0).getLogradouro());
        assertEquals("8670-065", response.getBody().get(0).getCep());
        assertEquals("Rio de Janeiro/RJ", response.getBody().get(0).getCidade());
        assertEquals(true, response.getBody().get(0).getEnderecoPrincipal());
    }

    @Test
    void whenFindByIdThenReturnSucess() {
        when(enderecoAssembler.toModel(endereco)).thenReturn(enderecoResponseDTO);
        when(enderecoService.findById(any(UUID.class))).thenReturn(endereco);

        ResponseEntity<EnderecoResponseDTO> response = controller.findById(UUID.randomUUID());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(EnderecoResponseDTO.class, response.getBody().getClass());

        assertEquals("7038b81b-f538-43f5-8c5e-537ee2ffdc46",response.getBody().getId());
        assertEquals(10, response.getBody().getNumero());
        assertEquals("Recreio", response.getBody().getLogradouro());
        assertEquals("8670-065", response.getBody().getCep());
        assertEquals("Rio de Janeiro/RJ", response.getBody().getCidade());
        assertEquals(true, response.getBody().getEnderecoPrincipal());
    }

    @Test
    void findEnderecoPrincipal() {
        when(enderecoAssembler.toModel(endereco)).thenReturn(enderecoResponseDTO);
        when(enderecoService.findEnderecoPrincipal(any(UUID.class))).thenReturn(endereco);

        ResponseEntity<EnderecoResponseDTO> response = controller.findEnderecoPrincipal(UUID.randomUUID());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(EnderecoResponseDTO.class, response.getBody().getClass());

        assertEquals("7038b81b-f538-43f5-8c5e-537ee2ffdc46",response.getBody().getId());
        assertEquals(10, response.getBody().getNumero());
        assertEquals("Recreio", response.getBody().getLogradouro());
        assertEquals("8670-065", response.getBody().getCep());
        assertEquals("Rio de Janeiro/RJ", response.getBody().getCidade());
        assertEquals(true, response.getBody().getEnderecoPrincipal());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(enderecoAssembler.toEntity(enderecoRequestDTO)).thenReturn(endereco);
        when(enderecoService.save(any())).thenReturn(endereco);

        ResponseEntity<?> response = controller.save(enderecoRequestDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}