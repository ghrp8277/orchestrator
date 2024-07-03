package com.example.orchestrator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetStockDto {
    private String symbol;
    private String timeframe;
    private int count;
}
