package com.example.orchestrator.controller;

import com.example.orchestrator.service.StockService;
import com.example.orchestrator.util.GrpcResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.grpc.*;

@RestController
@RequestMapping("/stock")
public class StockController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    private final StockService stockService;
    private final GrpcResponseHelper grpcResponseHelper;

    @Autowired
    public StockController(StockService stockService, GrpcResponseHelper grpcResponseHelper) {
        this.stockService = stockService;
        this.grpcResponseHelper = grpcResponseHelper;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        Response response = stockService.getStockData();
        return grpcResponseHelper.createJsonResponse(response);
    }
}
