package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.AcessorioRequest;
import br.com.fiap.concessionaria.dto.request.FabricanteRequest;
import br.com.fiap.concessionaria.dto.response.FabricanteResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.Collection;
import java.util.Objects;

public class FabricanteService implements ServiceDTO<Fabricante, FabricanteRequest, FabricanteResponse>{

    @Autowired
    private FabricanteRepository repo;

    @Override
    public Collection<Fabricante> findAll(Example<Fabricante> example) {
        return repo.findAll( example );
    }

    @Override
    public Fabricante findById(Long id) {
        return repo.findById( id ).orElse(null);
    }

    @Override
    public Fabricante save(Fabricante e) {
        return repo.save( e );
    }

    @Override
    public Fabricante toEntity(FabricanteRequest dto) {
        if (Objects.isNull( dto )) return null;


        return Fabricante.builder()
                .nome(dto.nome())
                .nomeFantasia(dto.nomeFantasia())
                .build();
    }


    @Override
    public FabricanteResponse toResponse(Fabricante e) {
        return FabricanteResponse.builder()
                .nome(e.getNome())
                .nomeFantasia(e.getNomeFantasia())
                .build();

    }
}
