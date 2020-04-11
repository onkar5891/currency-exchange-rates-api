package org.currencyexchange.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class ExternalRateResponse {
    private Map<String, BigDecimal> rates;
    private String base;
}
