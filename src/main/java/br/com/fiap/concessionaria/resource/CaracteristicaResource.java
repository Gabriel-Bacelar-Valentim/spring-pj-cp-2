package br.com.fiap.concessionaria.resource;


import br.com.fiap.concessionaria.dto.request.CaracteristicaRequest;
import br.com.fiap.concessionaria.dto.response.CaracteristicaResponse;
import br.com.fiap.concessionaria.entity.Caracteristica;
import br.com.fiap.concessionaria.service.CaracteristicaService;
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
@RequestMapping(value = "/caracteristica")
public class CaracteristicaResource {

    @Autowired
    private CaracteristicaService service;

    @GetMapping
    public ResponseEntity<Collection<CaracteristicaResponse>> findall(
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "veiculo.id", required = false) Long idVeiculo
    ) {

        var caracteristica = Caracteristica.builder()
                .id(idVeiculo)
                .nome(nome)
                .descricao(descricao)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();
        Example<Caracteristica> example = Example.of(caracteristica, matcher);
        Collection<Caracteristica> caracteristicaList = service.findAll(example);

        if (caracteristicaList.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = caracteristicaList.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CaracteristicaResponse> findbyid(Long id) {
        var caracteristicaList = service.findById(id);
        if (caracteristicaList == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse(caracteristicaList);
        return ResponseEntity.ok(resposta);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CaracteristicaResponse> save(@RequestBody @Valid CaracteristicaRequest r) {
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
