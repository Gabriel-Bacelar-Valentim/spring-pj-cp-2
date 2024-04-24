package br.com.fiap.concessionaria.resource;


import br.com.fiap.concessionaria.dto.request.AcessorioRequest;
import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.AcessorioResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Acessorio;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.service.VeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuario")
public class VeiculoResource {

    @Autowired
    private VeiculoService service;

    @GetMapping
    public ResponseEntity<Collection<VeiculoResponse>>findall(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "cor", required = false) String cor,
            @RequestParam(name = "cilindradas", required = false) Short cilindradas,
            @RequestParam(name = "preco", required = false) Double preco,
            @RequestParam(name = "modelo", required = false) String modelo,
            @RequestParam(name = "palavraDeEfeito", required = false) String palavraDeEfeito,
            @RequestParam(name = "fabricante", required = false) Fabricante fabricante,
            @RequestParam(name = "acessorios", required = false) Set acessorios,
            @RequestParam(name = "anoDeFabricacao", required = false) Year anoDeFabricacao,
            @RequestParam(name = "tipo", required = false)TipoVeiculo tipo


            ){
        var veiculo = Veiculo.builder()
                .id( id )
                .nome( nome )
                .cor( cor )
                .cilindradas( cilindradas )
                .preco( preco )
                .modelo( modelo )
                .palavraDeEfeito( palavraDeEfeito )
                .fabricante( fabricante )
                .acessorios( acessorios )
                .anoDeFabricacao( anoDeFabricacao )
                .tipo( tipo )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
        Example<Veiculo> example = Example.of( veiculo, matcher );
        Collection<Veiculo> veiculoList = service.findAll( example );

        if ( veiculoList.isEmpty() ) return ResponseEntity.notFound().build();

        var resposta = veiculoList.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( resposta );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoResponse> findbyId(Long id){
        var veiculoList = service.findById( id );
        if (veiculoList==null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( veiculoList );
        return ResponseEntity.ok(resposta);
    }
    @Transactional
    @PostMapping
    public ResponseEntity<VeiculoResponse> save(@RequestBody @Valid VeiculoRequest r){
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

    public List<AcessorioResponse> toAcessorioResponse(List<Acessorio> acessorios) {
        List<AcessorioResponse> responseList = new ArrayList<>();
        for (Acessorio acessorio : acessorios) {
            responseList.add(new AcessorioResponse(acessorio.getNome(), acessorio.getPreco(), acessorio.getId()));
        }
        return responseList;
    }

    @GetMapping(value = "/{id}/acessorios")
    public ResponseEntity<AcessorioResponse> findAcessoriosByVeiculoId(@PathVariable Long id){
        var veiculo = service.findById(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        }
        var acessorios = service.findAcessoriosByVeiculo(veiculo);
        var resposta = acessorios.stream()
                .map(this::toAcessorioResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }



}
