package org.br.mineradora.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.br.mineradora.dto.CurrencyPriceDTO;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"USDBRL\":{\"code\":\"USD\",\"codein\":\"BRL\",\"name\":\"Dólar Americano/Real Brasileiro\",\"high\":\"5.4583\",\"low\":\"5.4266\",\"varBid\":\"-0.0046\",\"pctChange\":\"0\",\"bid\":\"5.4329\",\"ask\":\"5.4355\",\"timestamp\":\"1727481598\",\"create_date\":\"2024-09-27 20:59:58\"}}";
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CurrencyPriceDTO currencyPriceDTO = objectMapper.readValue(json, CurrencyPriceDTO.class);
        System.out.println(currencyPriceDTO.getUsdbrl().getBid());
    }

}
