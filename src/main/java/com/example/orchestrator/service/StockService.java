package com.example.orchestrator.service;

import com.example.grpc.StockServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockServiceGrpc stockServiceGrpc;

    public Response getStockData() {

    }
}
