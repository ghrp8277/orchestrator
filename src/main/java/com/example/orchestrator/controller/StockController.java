package com.example.orchestrator.controller;

import com.example.orchestrator.dto.GetStockDataDto;
import com.example.orchestrator.service.StockService;
import com.example.orchestrator.util.GrpcResponseHelper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/markets")
    public ResponseEntity<String> getMarkets() {
        Response response = stockService.getMarkets();
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/markets/{marketName}")
    public ResponseEntity<String> getMarketByName(@PathVariable String marketName) {
        Response response = stockService.getStocksByMarket(marketName);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/markets/{marketName}/securities/{code}")
    public ResponseEntity<String> getStockByCode(
            @PathVariable String marketName,
            @PathVariable String code,
            @Valid @ModelAttribute GetStockDataDto getStockDataDto
    ) {
        Response response = stockService.getStockByCode(marketName, code, getStockDataDto);
        return grpcResponseHelper.createJsonResponse(response);
    }
}
