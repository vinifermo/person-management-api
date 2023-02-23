package com.attornatus.backend.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponseDTO {
    private String nome;
    private LocalDate dataNascimento;
    private List<EnderecoResponseDTO> endereco;

}
