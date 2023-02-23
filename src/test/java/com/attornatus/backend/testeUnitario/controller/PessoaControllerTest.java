package com.attornatus.backend.testeUnitario.controller;

import com.attornatus.backend.api.assembler.EnderecoAssembler;
import com.attornatus.backend.api.assembler.PessoaAssembler;
import com.attornatus.backend.api.controller.PessoaController;
import com.attornatus.backend.api.dto.request.PessoaRequestDTO;
import com.attornatus.backend.api.dto.response.EnderecoResponseDTO;
import com.attornatus.backend.api.dto.response.PessoaResponseDTO;
import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.model.Pessoa;
import com.attornatus.backend.domain.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class PessoaControllerTest {

    private PessoaController controller;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private PessoaAssembler pessoaAssembler;

    @Mock
    private EnderecoAssembler enderecoAssembler;

    @Mock
    private RequestAttributes attrs;

    private Pessoa pessoa = new Pessoa();
    private PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
    private PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();

    private Endereco endereco = new Endereco();
    private EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();

    private void startPessoa() {
        endereco = new Endereco(UUID.fromString("7038b81b-f538-43f5-8c5e-537ee2ffdc46"), "Recreio", "8670-065", 10, "Rio de Janeiro/RJ",true,pessoa);
        enderecoResponseDTO = new EnderecoResponseDTO("7038b81b-f538-43f5-8c5e-537ee2ffdc45", "Recreio", "8670-065", 10, "Rio de Janeiro/RJ",true);

        pessoa = new Pessoa(UUID.fromString("7038b81b-f538-43f5-8c5e-537ee2ffdc46"), "Vincius", LocalDate.of(1995, 9, 21), List.of(endereco));
        pessoaResponseDTO = new PessoaResponseDTO( "Vinicius", LocalDate.of(1995, 9, 21), List.of(enderecoResponseDTO));
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPessoa();
        RequestContextHolder.setRequestAttributes(attrs);
        this.controller = new PessoaController(pessoaService, pessoaAssembler, enderecoAssembler);
    }

    @Test
    void whenFindByIdThenReturnSucess() {
        when(pessoaAssembler.toModel(pessoa)).thenReturn(pessoaResponseDTO);
        when(pessoaService.findById(any(UUID.class))).thenReturn(pessoa);

        ResponseEntity<PessoaResponseDTO> response = controller.findById(UUID.randomUUID());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PessoaResponseDTO.class, response.getBody().getClass());

        assertEquals("Vinicius", response.getBody().getNome());
        assertEquals(LocalDate.of(1995, 9, 21), response.getBody().getDataNascimento());
        assertEquals(List.of(enderecoResponseDTO), response.getBody().getEndereco());

    }

    @Test
    void whenFindAllThenReturnAListOfPessoas() {
        when(pessoaAssembler.toCollectionModel(List.of(pessoa))).thenReturn(List.of(pessoaResponseDTO));
        when(pessoaService.findAll()).thenReturn(List.of(pessoa));

        ResponseEntity<List<PessoaResponseDTO>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PessoaResponseDTO.class, response.getBody().get(0).getClass());

        assertEquals("Vinicius", response.getBody().get(0).getNome());
        assertEquals(LocalDate.of(1995, 9, 21), response.getBody().get(0).getDataNascimento());
        assertEquals(List.of(enderecoResponseDTO), response.getBody().get(0).getEndereco());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(pessoaAssembler.toEntity(pessoaRequestDTO)).thenReturn(pessoa);
        when(pessoaService.save(any())).thenReturn(pessoa);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<?> response = controller.save(pessoaRequestDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void whenUpdateThenReturnNoContent() {
        lenient().when(pessoaAssembler.toEntity(pessoaRequestDTO)).thenReturn(pessoa);
        lenient().when(pessoaService.save(any())).thenReturn(pessoa);

        ResponseEntity<?> response = controller.update(UUID.randomUUID(), pessoaRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }
}