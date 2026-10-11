package com.ms.pedido.ms_pedido.repository;

import com.ms.pedido.ms_pedido.model.CertificadoSag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificadoSagRepository extends JpaRepository<CertificadoSag,Long> {

    Optional<CertificadoSag> findByPedidoIdPedido(Long idPedido);

    
}