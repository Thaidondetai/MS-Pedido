package com.ms.pedido.ms_pedido.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "envios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private String rutReceptor;

    @Column(nullable = false)
    private String nombreReceptor;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String comuna;

    @Column(nullable = false)
    private String direccion;

    private String indicaciones;
}