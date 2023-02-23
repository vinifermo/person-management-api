package com.attornatus.backend.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaRequestDTO {
    @NotBlank(message = "Campo nome não pode estar vazio.")
    private String nome;

    @NotNull(message = "A data de nascimento deve ser informado.")
    private LocalDate dataNascimento;
    private List<EnderecoRequestDTO> endereco;

}
