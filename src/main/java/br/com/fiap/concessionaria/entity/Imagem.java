package br.com.fiap.concessionaria.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_IMAGEM")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_IMAGEM")
    @SequenceGenerator(name = "SQ_IMAGEM", sequenceName = "SQ_IMAGEM", allocationSize = 1)
    @Column(name = "ID_IMAGEM")
    private Long id;

    private String uri;

    private Boolean principal;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "VEICULO",
            referencedColumnName = "ID_VEICULO",
            foreignKey = @ForeignKey(
                    name = "FK_IMAGEM_VEICULO"
            )
    )
    private Veiculo veiculo;

}
