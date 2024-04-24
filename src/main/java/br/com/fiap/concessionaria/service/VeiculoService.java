package br.com.fiap.concessionaria.service;

import br.com.fiap.concessionaria.dto.request.AbstractRequest;
import br.com.fiap.concessionaria.dto.request.VeiculoRequest;
import br.com.fiap.concessionaria.dto.response.FabricanteResponse;
import br.com.fiap.concessionaria.dto.response.VeiculoResponse;
import br.com.fiap.concessionaria.entity.Fabricante;
import br.com.fiap.concessionaria.entity.TipoVeiculo;
import br.com.fiap.concessionaria.entity.Veiculo;
import br.com.fiap.concessionaria.repository.FabricanteRepository;
import br.com.fiap.concessionaria.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class VeiculoService implements ServiceDTO<Veiculo, VeiculoRequest, VeiculoResponse>{

    @Autowired
    private VeiculoRepository repo;

    @Autowired
    private TipoVeiculoService tipoVeiculoService;

    @Autowired
    private FabricanteService fabricanteService;



    @Override
    public Collection<Veiculo> findAll(Example<Veiculo> example) {
        return repo.findAll();
    }

    @Override
    public Veiculo findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Veiculo save(Veiculo e) {
        return repo.save(e);
    }

    @Override
    public Veiculo toEntity(VeiculoRequest dto) {

        if (Objects.isNull( dto )) return null;

        var fabricante = this.findById( dto.fabricante().id() );
        var tipo = this.findById( dto.tipo().id());


        return Veiculo.builder()
                .nome( dto.nome() )
                .cor( dto.cor() )
                .preco( dto.preco() )
                .cilindradas( dto.cilindradas() )
                .modelo( dto.modelo() )
                .palavraDeEfeito( dto.palavraDeEfeito() )
                .anoDeFabricacao( dto.anoDeFabricacao() )
                .fabricante(fabricante.getFabricante())
                .tipo(tipo.getTipo())
                .build();
    }

    @Override
    public VeiculoResponse toResponse(Veiculo e) {



        return VeiculoResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .cor( e.getCor() )
                .preco( e.getPreco() )
                .cilindradas( e.getCilindradas() )
                .modelo( e.getModelo() )
                .palavraDeEfeito( e.getPalavraDeEfeito() )
                .anoDeFabricacao( e.getAnoDeFabricacao() )
                .fabricante( fabricanteService.toResponse(Fabricante.builder().build()))
                .tipo( tipoVeiculoService.toResponse(TipoVeiculo.builder().build()))
                .build();
    }

    public Object findAcessoriosByVeiculo(Veiculo veiculo) {

        return null;
    }
}
