package br.com.fiap.concessionaria.resource;


import br.com.fiap.concessionaria.dto.request.AcessorioRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.service.AcessorioService;
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
@RequestMapping(value = "/acessorio")
public class AcessorioResource {

    @Autowired
    private AcessorioService service;

    @GetMapping
    public ResponseEntity<Collection<AcessorioResponse>> findall(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "preco", required = false) Double preco,
            @RequestParam(name = "id", required = false) Long id

    ){

        var acessorio = Acessorio.builder()
                .nome( nome )
                .preco( preco )
                .id( id )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
        Example<Acessorio> example = Example.of( acessorio, matcher );
        Collection<Acessorio> acessorioList = service.findAll( example );

        if ( acessorioList.isEmpty() ) return ResponseEntity.notFound().build();

        var resposta = acessorioList.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( resposta );

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AcessorioResponse> findbyId(Long id){
        var acessorioList = service.findById( id );
        if (acessorioList==null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( acessorioList );
        return ResponseEntity.ok(resposta);

    }

    @Transactional
    @PostMapping
    public ResponseEntity<AcessorioResponse> save(@RequestBody @Valid AcessorioRequest r){
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
