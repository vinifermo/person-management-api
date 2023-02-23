package com.attornatus.backend.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDTO {
    @NotNull
    private String id;
    @NotNull(message = "O logradouro deve ser informado.")
    private String logradouro;

    @NotNull(message = "O cep deve ser informado.")
    private String cep;

    @NotNull(message = "O número deve ser informado.")
    private int numero;

    @NotNull(message = "A cidade deve ser informado.")
    private String cidade;

    @NotNull
    private boolean isEnderecoPrincipal;

}
