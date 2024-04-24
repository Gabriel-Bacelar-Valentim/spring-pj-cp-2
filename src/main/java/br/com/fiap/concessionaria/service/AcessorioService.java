package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.AcessorioRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AcessorioService implements ServiceDTO<Acessorio, AcessorioRequest, AcessorioResponse>{


    @Autowired

    @Override
    public Collection<Acessorio> findAll(Example<Acessorio> example) {
        return null;
    }

    @Override
    public Acessorio findById(Long id) {
        return null;
    }

    @Override
    public Acessorio save(Acessorio e) {
        return null;
    }

    @Override
    public Acessorio toEntity(AcessorioRequest dto) {
        return null;
    }

    @Override
    public AcessorioResponse toResponse(Acessorio e) {
        return null;
    }
}
