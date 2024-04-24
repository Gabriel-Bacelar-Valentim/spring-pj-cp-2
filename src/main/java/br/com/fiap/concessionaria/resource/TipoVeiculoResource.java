package br.com.fiap.concessionaria.resource;


import br.com.fiap.concessionaria.dto.request.AcessorioRequest;
import br.com.fiap.concessionaria.dto.request.TipoVeiculoRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.dto.response.TipoVeiculoResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.service.TipoVeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/usuario")
public class TipoVeiculoResource {

    @Autowired
    private TipoVeiculoService service;

    @GetMapping
    ResponseEntity<Collection<TipoVeiculoResponse>> findall(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "id", required = false) Long id
    ){
        var tipoVeiculo = TipoVeiculo.builder()
                .nome( nome )
                .id( id )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
        Example<TipoVeiculo> example = Example.of( tipoVeiculo, matcher );
        Collection<TipoVeiculo> tipoVeiculoList = service.findAll( example );

        if ( tipoVeiculoList.isEmpty() ) return ResponseEntity.notFound().build();

        var resposta = tipoVeiculoList.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( resposta );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoVeiculoResponse> findbyId(Long id){
        var tipoVeiculoList = service.findById( id );
        if (tipoVeiculoList==null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( tipoVeiculoList );
        return ResponseEntity.ok(resposta);
    }
    @Transactional
    @PostMapping
    public ResponseEntity<TipoVeiculoResponse> save(@RequestBody @Valid TipoVeiculoRequest r){
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );//201
    }

}
