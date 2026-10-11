package com.ms.pedido.ms_pedido.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificadoSagDTO {

    @NotBlank(message = "El número de registro SAG es obligatorio para animales exóticos.")
    private String numeroRegistroSag;

    private String urlDocumento;

}