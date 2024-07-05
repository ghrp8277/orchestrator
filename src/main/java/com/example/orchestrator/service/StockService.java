package com.example.orchestrator.service;

import com.example.orchestrator.dto.GetStockDataDto;
import com.example.orchestrator.service.grpc.StockGrpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockGrpcService stockGrpcService;

    public Response getMarkets() {
        Empty empty = Empty.newBuilder().build();
        return stockGrpcService.getMarkets(empty);
    }

    public Response getStocksByMarket(String marketName) {
        GetStocksByMarketRequest getMarketByNameRequest = GetStocksByMarketRequest.newBuilder()
                .setMarketName(marketName)
                .build();

        return stockGrpcService.getStocksByMarket(getMarketByNameRequest);
    }

    public Response getStockByCode(
            String marketName,
            String code,
            GetStockDataDto getStockDataDto
    ) {
        GetStockDataByMarketAndCodeRequest getStockDataByMarketAndCodeRequest = GetStockDataByMarketAndCodeRequest.newBuilder()
                .setMarketName(marketName)
                .setCode(code)
                .setTimeframe(getStockDataDto.getTimeframe())
                .build();

        return stockGrpcService.getStockDataByMarketAndCode(getStockDataByMarketAndCodeRequest);
    }
}
