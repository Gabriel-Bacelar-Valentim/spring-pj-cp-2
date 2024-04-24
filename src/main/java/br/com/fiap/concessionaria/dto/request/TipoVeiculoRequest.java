package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TipoVeiculoRequest(
        @Size(min = 5, max = 100)
        @NotNull(message = "O tipo do veiculo deve ser informado")
        String nome
) {
}
