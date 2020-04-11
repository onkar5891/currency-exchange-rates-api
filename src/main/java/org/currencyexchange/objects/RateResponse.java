package org.currencyexchange.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.currencyexchange.entity.Rate;

import java.util.List;

@Data
@AllArgsConstructor
public class RateResponse {
    private List<Rate> rates;
    private String base;
}
