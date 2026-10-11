package com.ms.pedido.ms_pedido.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certificados_sag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificadoSag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCertificado;

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private String numeroRegistroSag;

    private String urlDocumento;

    @Column(nullable = false)
    private String estadoValidacion; 
}