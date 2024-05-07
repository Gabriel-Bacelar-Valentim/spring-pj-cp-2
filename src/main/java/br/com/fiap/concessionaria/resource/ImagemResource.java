package br.com.fiap.concessionaria.resource;


import br.com.fiap.concessionaria.dto.request.AbstractRequest;
import br.com.fiap.concessionaria.dto.request.ImagemRequest;
import br.com.fiap.concessionaria.dto.response.ImagemResponse;
import br.com.fiap.concessionaria.entity.Imagem;
import br.com.fiap.concessionaria.service.ImagemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;

@RestController
@RequestMapping(value = "/imagens")
public class ImagemResource implements ResourceDTO<ImagemRequest, ImagemResponse>{

    @Autowired
    private ImagemService service;



    @Override
    public ResponseEntity<ImagemResponse> findById(Long id) {
        var entity = service.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity);

        return ResponseEntity.ok(response);

    }

    @Transactional
    @PostMapping(value = "/{id}/imagens")
    public ResponseEntity<ImagemResponse> save(@PathVariable Long id, @RequestBody ImagemRequest dto, MultipartFile file) {

        var request = ImagemRequest.builder()
                .veiculo(new AbstractRequest(id))
                //.file(file)
                .build();

        Imagem entity = service.toEntity(request);

        var response = service.toResponse(entity);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.created(uri).body(response);


    }
}
