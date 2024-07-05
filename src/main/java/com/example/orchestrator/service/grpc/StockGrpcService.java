package com.example.orchestrator.service.grpc;

import com.example.grpc.*;

public interface StockGrpcService {
    Response getMarkets(Empty empty);
    Response getStocksByMarket(GetStocksByMarketRequest getStocksByMarketRequest);
    Response getStockDataByMarketAndCode(GetStockDataByMarketAndCodeRequest getStockDataByMarketAndCodeRequest);
}
