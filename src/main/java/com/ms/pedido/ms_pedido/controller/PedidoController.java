package com.ms.pedido.ms_pedido.controller;

import com.ms.pedido.ms_pedido.dto.PedidoRequestDTO;
import com.ms.pedido.ms_pedido.dto.PedidoResponseDTO;
import com.ms.pedido.ms_pedido.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class PedidoController {

    private final PedidoService pedidoService;
    
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO request) {
        PedidoResponseDTO response = pedidoService.crearPedido(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPedidosPorUsuario(@PathVariable String idUsuario) {
        List<PedidoResponseDTO> pedidos = pedidoService.obtenerPedidosPorUsuario(idUsuario);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedidoPorId(@PathVariable Long idPedido) {
        PedidoResponseDTO pedido = pedidoService.obtenerPedidoPorId(idPedido);
        return ResponseEntity.ok(pedido);
    }
}