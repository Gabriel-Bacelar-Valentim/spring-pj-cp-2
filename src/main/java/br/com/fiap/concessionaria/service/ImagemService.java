package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.ImagemRequest;
import br.com.fiap.concessionaria.dto.response.ImagemResponse;
import br.com.fiap.concessionaria.entity.Imagem;
import br.com.fiap.concessionaria.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ImagemService implements ServiceDTO<Imagem, ImagemRequest, ImagemResponse> {

    @Autowired
    private ImagemRepository repo;

    @Autowired
    private VeiculoService veiculoService;


    @Override
    public Collection<Imagem> findAll(Example<Imagem> example) {
        return repo.findAll(example);
    }

    @Override
    public Imagem findById(Long id) {
        return repo.findById( id ).orElse(null) ;
    }

    @Override
    public Imagem save(Imagem e) {
        return repo.save( e );
    }

    @Override
    public Imagem toEntity(ImagemRequest dto) {
        var veiculo = veiculoService.findById(dto.veiculo().id());

        return Imagem.builder()
                .principal( dto.principal())
                .uri(upload(dto.file()))
                .veiculo(veiculo)
                .build();
    }

    @Override
    public ImagemResponse toResponse(Imagem e) {

        var veiculo = veiculoService.toResponse(e.getVeiculo());

         return ImagemResponse.builder()
                .principal(e.getPrincipal())
                 .uri(e.getUri())
                 .veiculo(veiculo)
                 .id(e.getId())
                 .build();

    }
    public String upload(Byte[] file){
        return "";
    }
}
