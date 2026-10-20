package com.ms.pedido.ms_pedido.service;

import com.ms.pedido.ms_pedido.dto.PedidoResponseDTO;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsProducerService {

    private final SqsTemplate sqsTemplate;

    @Value("${app.sqs.queue-pedidos}")
    private String queueName;

    public void publicarPedidoRealizado(PedidoResponseDTO pedido) {
        try {
            sqsTemplate.send(to -> to.queue(queueName).payload(pedido));
            log.info("Evento PedidoRealizado enviado exitosamente a SQS para el pedido ID: {}", pedido.getIdPedido());
        } catch (Exception e) {
            log.error("Error al publicar evento en SQS para el pedido ID: {}", pedido.getIdPedido(), e);
        }
    }
}