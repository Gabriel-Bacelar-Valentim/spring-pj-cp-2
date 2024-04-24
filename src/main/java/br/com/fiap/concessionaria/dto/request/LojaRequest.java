package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LojaRequest(

        @Size(min = 5, max = 100)
        @NotNull(message = "O nome da loja deve ser informado")
        String nome
) {
}
