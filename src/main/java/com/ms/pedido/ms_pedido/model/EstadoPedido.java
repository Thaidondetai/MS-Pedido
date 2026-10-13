package com.ms.pedido.ms_pedido.model;

public enum EstadoPedido {
    PENDIENTE_PAGO,          
    PENDIENTE_VALIDACION_SAG,
    PAGADO,
    EN_PREPARACION,
    ENVIADO,
    ENTREGADO,
    RECHAZADO,               
    RECHAZADO_SAG,
    CANCELADO
}