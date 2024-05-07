package br.com.fiap.concessionaria.dto.request;

import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import lombok.Builder;


@Builder
public record ImagemRequest(

        Byte[] file,

        Boolean principal,

        AbstractRequest veiculo



) {
}
