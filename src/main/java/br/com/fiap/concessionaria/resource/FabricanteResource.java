package br.com.fiap.concessionaria.resource;


import br.com.fiap.concessionaria.dto.request.CaracteristicaRequest;
import br.com.fiap.concessionaria.dto.request.FabricanteRequest;
import br.com.fiap.concessionaria.dto.response.CaracteristicaResponse;
import br.com.fiap.concessionaria.dto.response.FabricanteResponse;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.service.FabricanteService;
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
@RequestMapping(value = "/fabricante")
public class FabricanteResource {

    @Autowired
    private FabricanteService service;

    @GetMapping
    public ResponseEntity<Collection<FabricanteResponse>> findall(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "nomeFantasia", required = false) String nomeFantasia,
            @RequestParam(name = "id", required = false) Long id
    ) {
        var fabricante = Fabricante.builder()
                .id(id)
                .nome(nome)
                .nomeFantasia(nomeFantasia)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
        Example<Fabricante> example = Example.of(fabricante, matcher);
        Collection<Fabricante> fabricanteList = service.findAll(example);

        if (fabricanteList.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = fabricanteList.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FabricanteResponse> findbyid(Long id) {
        var fabricanteList = service.findById(id);
        if (fabricanteList == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse(fabricanteList);
        return ResponseEntity.ok(resposta);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<FabricanteResponse> save(@RequestBody @Valid FabricanteRequest r) {
        var entity = service.toEntity(r);
        var saved = service.save(entity);
        var resposta = service.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);//201

    }
}
