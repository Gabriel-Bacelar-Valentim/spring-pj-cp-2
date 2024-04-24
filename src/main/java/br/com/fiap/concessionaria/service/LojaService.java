package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.LojaRequest;
import br.com.fiap.concessionaria.dto.response.LojaResponse;
import br.com.fiap.concessionaria.entity.Loja;
import br.com.fiap.concessionaria.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LojaService implements ServiceDTO<Loja, LojaRequest, LojaResponse> {
    @Autowired
    private LojaRepository repo;
    @Autowired
    private VeiculoService veiculoService;
    @Override
    public Collection<Loja> findAll(Example<Loja> example) {
        return repo.findAll();
    }
    @Override
    public Loja findById(Long id) {
        return repo.findById(id).orElse(null);
    }
    @Override
    public Loja save(Loja e) {
        return repo.save(e);
    }
    @Override
    public Loja toEntity(LojaRequest dto) {
        return Loja.builder()
                .nome(dto.nome())
                .build();
    }
    @Override
    public LojaResponse toResponse(Loja e) {
        var lista = e.getVeiculosComercializados()
                .stream()
                .map(veiculoService::toResponse)
                .collect(Collectors.toSet());
        return LojaResponse.builder()
                .nome(e.getNome())
                .veiculosComercializados(lista)
                .build();
    }
}
