package com.ms.pedido.ms_pedido.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PedidoRequestDTO {

    @NotNull(message="El Id del usuario es obligatorio.")
    private String idUsuario;

    private String metodoPago;

    @Valid
    @NotNull(message="Los datos de envío son obligatorios.")
    private EnvioDTO envio;

    @Valid
    @NotEmpty(message="El pedido debe contener al menos un producto.")
    private List<DetallePedidoDTO> detalles;

    @Valid
    private CertificadoSagDTO certificadoSag;
}