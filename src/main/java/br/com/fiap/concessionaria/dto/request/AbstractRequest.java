package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(


        @Positive(message = "O id precisa ser positivo")
        @NotNull(message = "O id precisa ser informado")
        Long id
) {
    public Object toEntity(AbstractRequest fabricante) {


        return null;
    }
}
