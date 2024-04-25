package br.com.fiap.concessionaria.resource;


import br.com.fiap.concessionaria.dto.request.AbstractRequest;
import br.com.fiap.concessionaria.dto.request.LojaRequest;
import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.LojaResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.entity.Loja;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.repository.VeiculoRepository;
import br.com.fiap.concessionaria.service.LojaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/loja")
public class LojaResource {

    @Autowired
    private LojaService service;

    @Autowired
    private VeiculoRepository veiculoRepository;


    @GetMapping
    public ResponseEntity<Collection<LojaResponse>>findall(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "veiculosComercializados", required = false) Set veiculosComercializados
    ){

        var loja = Loja.builder()
                .id( id )
                .nome ( nome )
                .veiculosComercializados( veiculosComercializados )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
        Example<Loja> example = Example.of( loja, matcher );
        Collection<Loja> lojaList = service.findAll( example );

        if ( lojaList.isEmpty() ) return ResponseEntity.notFound().build();

        var resposta = lojaList.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( resposta );
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<LojaResponse> findbyId(Long id){
        var lojaList = service.findById( id );
        if (lojaList==null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( lojaList );
        return ResponseEntity.ok(resposta);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<LojaResponse> save(@RequestBody @Valid LojaRequest r){
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

    @Transactional
    @PostMapping(value = "/{id}/veiculos")
    public LojaResponse save(@PathVariable Long id, @RequestBody @Valid AbstractRequest veiculos) {
        if (Objects.isNull(veiculos)) return null;
        Loja loja = service.findById(id);
        Veiculo veiculoEntity = null;
        if (Objects.nonNull(veiculos.id())) {
            veiculoEntity = veiculoRepository.findById(veiculos.id()).orElseThrow();
        }
        loja.getVeiculosComercializados().add(veiculoEntity);
        return service.toResponse(loja);
    }

    @GetMapping(value = "/{id}/veiculos")
    public ResponseEntity<Collection<LojaResponse>> findLojabyVeiculos(@PathVariable Long id) {
        var loja = service.findByVeiculosId(id);
        var response = service.toResponse((Loja) loja);
        return ResponseEntity.ok(Collections.singleton(response));
    }

}
