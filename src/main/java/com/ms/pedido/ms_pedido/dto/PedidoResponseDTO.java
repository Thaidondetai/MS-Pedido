package com.ms.pedido.ms_pedido.dto;

import com.ms.pedido.ms_pedido.model.EstadoPedido;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PedidoResponseDTO {

    private Long idPedido;
    private String idUsuario;
    private LocalDateTime fechaPedido;
    private EstadoPedido estado;
    private BigDecimal montoTotal;
    private String metodoPago;
    private EnvioDTO envio;
    private List<DetallePedidoDTO> detalles;
    private CertificadoSagDTO certificadoSag;
}