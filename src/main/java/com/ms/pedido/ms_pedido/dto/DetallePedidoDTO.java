package com.ms.pedido.ms_pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedidoDTO {
    
    @NotNull(message = "El ID del producto es obligatorio.")
    private Long idProducto;

    @NotNull(message = "El nombre del producto es obligatorio.")
    private String nombreProducto;

    @NotNull(message = "El precio unitario es obligatorio.")
    @Min(value = 0, message = "El precio debe ser mayor o igual a cero.")
    private BigDecimal precioUnitario;

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad mínima es 1.")
    private Integer cantidad;

    @NotNull(message = "Debe indicar si el producto requiere validación SAG.")
    private Boolean requiereSag;
}