package br.com.fiap.concessionaria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_CARACTERISTICA", uniqueConstraints = {
/**
 * UK para garantir que não se tenha mais de uma caracteristica com o mesmo nome.
 */
        @UniqueConstraint(name = "UK_CARACTERISTICA_NOME", columnNames = {"NOME"})
})
public class Caracteristica {
    @Id
    @SequenceGenerator(name = "SQ_CARACTERISTICA", sequenceName = "SQ_CARACTERISTICA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CARACTERISTICA")
    @Column(name = "ID_CARACTERISTICA")
    private Long id;

    //30 digitos
    @Size(max = 30)
    @Column(name = "NOME")
    private String nome;
    //20 digitos
    @Size(max = 20)
    @Column(name = "DESCRICAO")
    private String descricao;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "VEICULO",
            referencedColumnName = "ID_VEICULO",
            foreignKey = @ForeignKey(
                    name = "FK_VEICULO_CARACTERISTICA"
            )
    )
    private Veiculo veiculo;
}