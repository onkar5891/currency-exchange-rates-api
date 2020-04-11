package org.currencyexchange.service;

import org.currencyexchange.entity.Rate;
import org.currencyexchange.objects.ExternalRateResponse;
import org.currencyexchange.objects.RateResponse;
import org.currencyexchange.repository.RateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class RateService {
    @Value("${base-currency}")
    private String baseCurrency;

    private final RateRepository rateRepository;

    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public RateResponse fetch() {
        List<Rate> rates = rateRepository.findAll();
        rates.sort(Comparator.comparing(Rate::getCode));
        return new RateResponse(rates, baseCurrency);
    }

    public void load(ExternalRateResponse externalRateResponse) {
        externalRateResponse.getRates().entrySet().stream().map(this::rateMapper).forEach(rateRepository::save);
    }

    private Rate rateMapper(Map.Entry<String, BigDecimal> rateEntry) {
        Rate rate = rateRepository.findByCode(rateEntry.getKey()).orElse(newRate(rateEntry.getKey()));
        rate.setValue(rateEntry.getValue());
        rate.setLastModified(new Timestamp(Instant.now().getEpochSecond()));

        return rate;
    }

    private Rate newRate(String code) {
        Rate rate = new Rate();
        rate.setCode(code);
        return rate;
    }
}
