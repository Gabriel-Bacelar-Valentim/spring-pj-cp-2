package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.TipoVeiculoRequest;
import br.com.fiap.concessionaria.dto.response.TipoVeiculoResponse;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.repository.TipoVeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TipoVeiculoService implements ServiceDTO<TipoVeiculo, TipoVeiculoRequest, TipoVeiculoResponse> {

    @Autowired
    private TipoVeiculoRepository repo;
    @Override
    public Collection<TipoVeiculo> findAll(Example<TipoVeiculo> example) {
        return repo.findAll();
    }
    @Override
    public TipoVeiculo findById(Long id) {
        return repo.findById(id).orElse(null);
    }
    @Override
    public TipoVeiculo save(TipoVeiculo e) {
        return repo.save(e);
    }
    @Override
    public TipoVeiculo toEntity(TipoVeiculoRequest dto) {
        return TipoVeiculo.builder()
                .nome(dto.nome())
                .build();
    }
    @Override
    public TipoVeiculoResponse toResponse(TipoVeiculo e) {
        return TipoVeiculoResponse.builder()
                .nome(e.getNome())
                .build();
    }
}
