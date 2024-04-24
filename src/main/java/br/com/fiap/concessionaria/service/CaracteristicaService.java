package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.CaracteristicaRequest;
import br.com.fiap.concessionaria.dto.response.CaracteristicaResponse;
import br.com.fiap.concessionaria.entity.Caracteristica;
import br.com.fiap.concessionaria.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import javax.xml.catalog.Catalog;
import java.util.Collection;
public class CaracteristicaService implements ServiceDTO<Caracteristica, CaracteristicaRequest, CaracteristicaResponse>{
    @Autowired
    private CaracteristicaRepository repo;
    @Autowired
    private VeiculoService veiculoService;
    @Override
    public Collection<Caracteristica> findAll(Example<Caracteristica> example) {
        return repo.findAll();
    }
    @Override
    public Caracteristica findById(Long id) {
        return repo.findById(id).orElse(null);
    }
    @Override
    public Caracteristica save(Caracteristica e) {
        return repo.save(e);
    }
    @Override
    public Caracteristica toEntity(CaracteristicaRequest dto) {
        var veiculo = veiculoService.findById(dto.veiculo().id());
        return Caracteristica.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .veiculo(veiculo)
                .build();
    }
    @Override
    public CaracteristicaResponse toResponse(Caracteristica e) {
        var veiculo = veiculoService.toResponse(e.getVeiculo());
        return CaracteristicaResponse.builder()
                .nome( e.getNome())
                .descricao(e.getDescricao())
                .veiculo(veiculo)
                .build();
    }
}
