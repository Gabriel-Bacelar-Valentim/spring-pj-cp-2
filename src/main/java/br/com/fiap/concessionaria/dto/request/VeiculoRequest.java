package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record VeiculoRequest(

        @Positive(message = "O preco deve ser positivo")
        @NotNull(message = "O preco nao pode ser nulo")
        Double preco,


        @NotNull(message = "O modelo do carro deve ser informado")
        String modelo,

        @PastOrPresent(message = "Nao aceitamos data do futuro")
        @NotNull(message = "O ano de fabricacao deve ser informado")
        Year anoDeFabricacao,

        @Size(min = 5, max = 100)
        @NotNull(message = "O nome da loja deve ser informado")
        String nome,


        @NotNull(message = "O tipo do veiculo nao pode ser nulo")
        AbstractRequest tipo,

        @Size(min = 3, max = 50, message = "Deve estar entres essa quantidade de caracteres")
        @NotNull(message = "A fabricante deve ser informada")
        AbstractRequest fabricante,

        @NotNull(message = "A cor do carro precisa ser informada")
        String cor,

        @Size(min = 4, max = 100, message = "Deve estar entres essa quantidade de caracteres")
        @NotNull(message = "Uma palavra de efeito deve ser informada")
        String palavraDeEfeito,

        @Positive(message = "Precisa ser um numero positivo")
        @NotNull(message = "É nescessario informar as cilindradas")
        Short cilindradas
) {
}
