package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Veiculo;
import org.springframework.data.domain.Example;

import java.util.Collection;

public class VeiculoService implements ServiceDTO<Veiculo, VeiculoRequest, VeiculoResponse>{
    @Override
    public Collection<Veiculo> findAll(Example<Veiculo> example) {
        return null;
    }

    @Override
    public Veiculo findById(Long id) {
        return null;
    }

    @Override
    public Veiculo save(Veiculo e) {
        return null;
    }

    @Override
    public Veiculo toEntity(VeiculoRequest dto) {
        return null;
    }

    @Override
    public VeiculoResponse toResponse(Veiculo e) {
        return null;
    }
}
