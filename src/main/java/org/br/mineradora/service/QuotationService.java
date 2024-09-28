package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mineradora.client.CurrencyPriceClient;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class QuotationService {

    @Inject
    @RestClient
    private CurrencyPriceClient currencyPriceClient;

    @Inject
    private QuotationRepository quotationRepository;

    @Inject
    private KafkaEvents kafkaEvents;

    public void getCurrencyPrice(){

        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

        if(updateCurrentInfoPrice(currencyPriceInfo)){
            kafkaEvents.sendNewKafkaEvent(QuotationDTO.builder()
                            .currencyPrice(new BigDecimal(currencyPriceInfo.getUsdbrl().getBid()))
                            .date(new Date())
                            .build());
        }
    }

    private boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceInfo) {
        BigDecimal currentPrice = new BigDecimal(currencyPriceInfo.getUsdbrl().getBid());
        boolean updatePrice = false;

        List<QuotationEntity> quotationList = quotationRepository.findAll().list();

        if(quotationList.isEmpty()){
            saveQuotation(currencyPriceInfo);
            updatePrice = true;
        } else {
            QuotationEntity lastDollarPrice = quotationList.get(quotationList.size() -1);

            if (currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()){
                updatePrice = true;
                saveQuotation(currencyPriceInfo);
            }
        }
        return updatePrice;
    }

    private void saveQuotation(CurrencyPriceDTO currencyPriceInfo){
        QuotationEntity quotation = new QuotationEntity();

        quotation.setDate(new Date());
        quotation.setCurrencyPrice(new BigDecimal(currencyPriceInfo.getUsdbrl().getBid()));
        quotation.setPctChange(currencyPriceInfo.getUsdbrl().getPctChange());
        quotation.setPair("USD-BRL");

        quotationRepository.persist(quotation);
    }


}