package br.com.fiap.concessionaria.dto.response;

import lombok.Builder;

@Builder
public record ImagemResponse(
        Long id,
        String uri,
        Boolean principal,
        VeiculoResponse veiculo
) {
}
