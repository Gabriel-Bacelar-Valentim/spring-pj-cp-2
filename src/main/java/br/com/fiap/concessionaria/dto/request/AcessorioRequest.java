package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AcessorioRequest(
        @Size(min = 5, max = 100)
        @NotNull(message = "O nome da loja deve ser informado")
        String nome,

        @Positive(message = "O preco deve ser positivo")
        @NotNull(message = "O preco nao pode ser nulo")
        Double preco
) {
}
