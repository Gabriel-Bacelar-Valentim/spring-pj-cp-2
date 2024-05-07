package br.com.fiap.concessionaria.repository;

import br.com.fiap.concessionaria.entity.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {




}
