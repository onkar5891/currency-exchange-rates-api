package org.currencyexchange.controller;

import org.currencyexchange.objects.ExternalRateResponse;
import org.currencyexchange.objects.RateResponse;
import org.currencyexchange.service.RateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/rates")
public class RateController {
    private final RateService rateService;
    private final RestTemplate restTemplate;

    @Value("${currency.exchange-rates.api.url}")
    private String currencyExchangeRatesApiUrl;

    public RateController(RateService rateService, RestTemplate restTemplate) {
        this.rateService = rateService;
        this.restTemplate = restTemplate;
    }

    @PutMapping
    public ResponseEntity<?> refreshRates() {
        ResponseEntity<ExternalRateResponse> rateResponse = restTemplate.getForEntity(currencyExchangeRatesApiUrl, ExternalRateResponse.class);
        if (rateResponse.getStatusCode() == HttpStatus.OK && nonNull(rateResponse.getBody())) {
            rateService.load(rateResponse.getBody());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(rateResponse.getStatusCode()).build();
    }

    @GetMapping
    public ResponseEntity<RateResponse> fetchRates() {
        return ResponseEntity.ok(rateService.fetch());
    }
}
