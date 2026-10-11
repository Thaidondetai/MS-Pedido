package com.ms.pedido.ms_pedido.service;

import com.ms.pedido.ms_pedido.dto.*;
import com.ms.pedido.ms_pedido.model.*;
import com.ms.pedido.ms_pedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        
        boolean requiereSag = request.getDetalles().stream()
                .anyMatch(DetallePedidoDTO::getRequiereSag);

        if (requiereSag && (request.getCertificadoSag() == null || request.getCertificadoSag().getNumeroRegistroSag().isBlank())) {
            throw new IllegalArgumentException("Uno o más productos corresponden a especies exóticas y requieren un número de registro SAG válido.");
        }

        EstadoPedido estadoInicial = requiereSag ? EstadoPedido.PENDIENTE_VALIDACION_SAG : EstadoPedido.PAGADO;

        BigDecimal montoTotal = request.getDetalles().stream()
                .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = Pedido.builder()
                .idUsuario(request.getIdUsuario())
                .fechaPedido(LocalDateTime.now())
                .estado(estadoInicial)
                .montoTotal(montoTotal)
                .metodoPago(request.getMetodoPago())
                .build();

        
        List<DetallePedido> detalles = request.getDetalles().stream().map(d -> 
            DetallePedido.builder()
                    .idProducto(d.getIdProducto())
                    .nombreProducto(d.getNombreProducto())
                    .precioUnitario(d.getPrecioUnitario())
                    .cantidad(d.getCantidad())
                    .requiereSag(d.getRequiereSag())
                    .build()
        ).collect(Collectors.toList());

        detalles.forEach(pedido::addDetalle);

        EnvioDTO envioDto = request.getEnvio();
        Envio envio = Envio.builder()
                .rutReceptor(envioDto.getRutReceptor())
                .nombreReceptor(envioDto.getNombreReceptor())
                .telefono(envioDto.getTelefono())
                .region(envioDto.getRegion())
                .comuna(envioDto.getComuna())
                .direccion(envioDto.getDireccion())
                .indicaciones(envioDto.getIndicaciones())
                .build();

        pedido.setEnvio(envio);

        if (requiereSag && request.getCertificadoSag() != null) {
            CertificadoSagDTO sagDto = request.getCertificadoSag();
            CertificadoSag sag = CertificadoSag.builder()
                    .numeroRegistroSag(sagDto.getNumeroRegistroSag())
                    .urlDocumento(sagDto.getUrlDocumento())
                    .estadoValidacion("PENDIENTE")
                    .build();
            pedido.setCertificadoSag(sag);
        }

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return mapearAResponseDTO(pedidoGuardado);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerPedidosPorUsuario(String idUsuario) {
        return pedidoRepository.findByIdUsuarioOrderByFechaPedidoDesc(idUsuario)
                .stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPedidoPorId(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + idPedido));
        return mapearAResponseDTO(pedido);
    }

    
    private PedidoResponseDTO mapearAResponseDTO(Pedido pedido) {
        EnvioDTO envioDto = EnvioDTO.builder()
                .rutReceptor(pedido.getEnvio().getRutReceptor())
                .nombreReceptor(pedido.getEnvio().getNombreReceptor())
                .telefono(pedido.getEnvio().getTelefono())
                .region(pedido.getEnvio().getRegion())
                .comuna(pedido.getEnvio().getComuna())
                .direccion(pedido.getEnvio().getDireccion())
                .indicaciones(pedido.getEnvio().getIndicaciones())
                .build();

        List<DetallePedidoDTO> detallesDto = pedido.getDetalles().stream().map(d ->
                DetallePedidoDTO.builder()
                        .idProducto(d.getIdProducto())
                        .nombreProducto(d.getNombreProducto())
                        .precioUnitario(d.getPrecioUnitario())
                        .cantidad(d.getCantidad())
                        .requiereSag(d.getRequiereSag())
                        .build()
        ).collect(Collectors.toList());

        CertificadoSagDTO sagDto = null;
        if (pedido.getCertificadoSag() != null) {
            sagDto = CertificadoSagDTO.builder()
                    .numeroRegistroSag(pedido.getCertificadoSag().getNumeroRegistroSag())
                    .urlDocumento(pedido.getCertificadoSag().getUrlDocumento())
                    .build();
        }

        return PedidoResponseDTO.builder()
                .idPedido(pedido.getIdPedido())
                .idUsuario(pedido.getIdUsuario())
                .fechaPedido(pedido.getFechaPedido())
                .estado(pedido.getEstado())
                .montoTotal(pedido.getMontoTotal())
                .metodoPago(pedido.getMetodoPago())
                .envio(envioDto)
                .detalles(detallesDto)
                .certificadoSag(sagDto)
                .build();
    }
}