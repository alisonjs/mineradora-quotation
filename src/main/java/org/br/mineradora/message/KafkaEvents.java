package org.br.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.br.mineradora.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Slf4j
public class KafkaEvents {

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotationDTO) {
        log.info("-- Sending Quotation for Kafka Subject --");
        quotationRequestEmitter.send(quotationDTO).toCompletableFuture().join();
    }

}
