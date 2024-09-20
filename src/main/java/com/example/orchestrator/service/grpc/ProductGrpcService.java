package com.example.orchestrator.service.grpc;

import com.example.grpc.*;

public interface ProductGrpcService {
    Response getAllPoints(Empty empty);
}
