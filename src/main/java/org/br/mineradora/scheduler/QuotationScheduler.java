package org.br.mineradora.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.br.mineradora.service.QuotationService;

@ApplicationScoped
@Slf4j
public class QuotationScheduler {

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task.job")
    public void scheduler(){
        log.info("-- Get currency information --");
        quotationService.getCurrencyPrice();
    }

}
