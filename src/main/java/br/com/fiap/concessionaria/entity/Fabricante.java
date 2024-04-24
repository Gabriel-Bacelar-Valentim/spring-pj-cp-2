package br.com.fiap.concessionaria.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "TB_FABRICANTE")
public class Fabricante {
    @Id
    @SequenceGenerator(name = "SQ_FABRICANTE", sequenceName = "SQ_FABRICANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FABRICANTE")
    @Column(name = "ID_FABRICANTE")
    private Long id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;

}
