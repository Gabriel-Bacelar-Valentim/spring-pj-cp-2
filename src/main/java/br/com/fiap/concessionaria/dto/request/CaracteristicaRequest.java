package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CaracteristicaRequest(

        @NotNull(message = "O veiculo precisa ser informado")
        AbstractRequest veiculo,

        @Size(min = 2, max = 255, message = "quantidade de caracteres do nome deve estar entre")
        @NotNull(message = "O nome precisa ser informado")
        String nome,

        @NotNull(message = "Descricao do veiculo necessaria")
        String descricao


) {
}
