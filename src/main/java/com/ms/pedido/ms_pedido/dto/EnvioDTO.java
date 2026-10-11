package com.ms.pedido.ms_pedido.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EnvioDTO {

    @NotBlank(message = "El RUT del receptor es obligatorio.")
    private String rutReceptor;

    @NotBlank(message = "El nombre del receptor es obligatorio.")
    private String nombreReceptor;

    @NotBlank(message = "El teléfono de contacto es obligatorio.")
    private String telefono;

    @NotBlank(message = "La región es obligatoria.")
    private String region;

    @NotBlank(message = "La comuna es obligatoria.")
    private String comuna;

    @NotBlank(message = "La dirección de envío es obligatoria.")
    private String direccion;

    private String indicaciones;
}