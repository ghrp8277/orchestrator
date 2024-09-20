package com.example.orchestrator.service;

import com.example.grpc.*;
import com.example.orchestrator.service.grpc.ProductGrpcService;
import com.example.orchestrator.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final JsonUtil jsonUtil;
    private final ProductGrpcService productGrpcService;

    public Response getAllPoints() {
        Empty empty = Empty.newBuilder().build();
        return productGrpcService.getAllPoints(empty);
    }
}
