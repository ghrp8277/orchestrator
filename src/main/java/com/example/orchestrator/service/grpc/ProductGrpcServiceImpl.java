package com.example.orchestrator.service.grpc;

import com.example.grpc.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

@Service
public class ProductGrpcServiceImpl implements ProductGrpcService {
    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;
    private final ManagedChannel channel;

    public ProductGrpcServiceImpl(@Value("${product.grpc.host}") String grpcHost, @Value("${product.grpc.port}") int grpcPort) {
        this.channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
        this.productServiceBlockingStub = ProductServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public Response getAllPoints(Empty empty) {
        return productServiceBlockingStub.getAllPoints(empty);
    }
}
