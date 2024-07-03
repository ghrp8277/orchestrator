package com.example.orchestrator.service.grpc;

import com.example.grpc.StockServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

@Service
public class StockGrpcServiceImpl implements StockGrpcService {
    private final StockServiceGrpc.StockServiceBlockingStub stockServiceBlockingStub;
    private final ManagedChannel channel;

    public StockGrpcServiceImpl(@Value("${stock.grpc.host}") String grpcHost, @Value("${stock.grpc.port}") int grpcPort) {
        this.channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
        this.stockServiceBlockingStub = StockServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public Response getStockData(GetStockDataRequest getStockDataRequest) {
        return stockServiceBlockingStub.getStockData(getStockDataRequest);
    }
}
