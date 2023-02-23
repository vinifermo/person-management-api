package com.attornatus.backend.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponseDTO {
    private String id;
    private String logradouro;
    private String cep;
    private int numero;
    private String cidade;
    private Boolean enderecoPrincipal;

}
