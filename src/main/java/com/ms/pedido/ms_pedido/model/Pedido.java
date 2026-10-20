package com.ms.pedido.ms_pedido.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(nullable = false)
    private String idUsuario;

    @Column(nullable = false)
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;

    private String metodoPago;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Envio envio;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private CertificadoSag certificadoSag;

    public void addDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
        if (envio != null) {
            envio.setPedido(this);
        }
    }

    public void setCertificadoSag(CertificadoSag certificado) {
        this.certificadoSag = certificado;
        if (certificado != null) {
            certificado.setPedido(this);
        }
    }
}