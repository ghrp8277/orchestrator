package com.example.orchestrator.service;

import com.example.orchestrator.constants.NaverSymbolConstants;
import com.example.orchestrator.service.grpc.StockGrpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockGrpcService stockGrpcService;

    public Response getStockData() {
        GetStockDataRequest getStockDataRequest = GetStockDataRequest.newBuilder()
                .setSymbol(NaverSymbolConstants.KOSPI.SK_HYNIX)
                .setTimeframe(NaverSymbolConstants.TimeFrame.DAY)
                .setCount(1250)
                .build();

        return stockGrpcService.getStockData(getStockDataRequest);
    }
}
