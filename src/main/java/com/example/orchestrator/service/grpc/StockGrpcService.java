package com.example.orchestrator.service.grpc;

import com.example.grpc.*;

public interface StockGrpcService {
    Response getStockData(GetStockDataRequest getStockDataRequest);
}
