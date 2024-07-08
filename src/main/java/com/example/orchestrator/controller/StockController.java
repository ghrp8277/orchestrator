package com.example.orchestrator.controller;

import com.example.orchestrator.dto.GetStockDataDto;
import com.example.orchestrator.dto.PaginationRequestDto;
import com.example.orchestrator.dto.SortParamsDto;
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

    @GetMapping("/markets/{marketName}/securities")
    public ResponseEntity<String> getAllStocks(
            @PathVariable String marketName,
            @Valid @ModelAttribute PaginationRequestDto paginationRequestDto,
            @Valid @ModelAttribute SortParamsDto sortParamsDto) {
        Response response = stockService.getAllStocksByMarket(marketName, paginationRequestDto, sortParamsDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/markets/{marketName}/securities/search/name")
    public ResponseEntity<String> searchStocksByName(
            @PathVariable String marketName,
            @RequestParam String name,
            @Valid @ModelAttribute PaginationRequestDto paginationRequestDto,
            @Valid @ModelAttribute SortParamsDto sortParamsDto
    ) {
        Response response = stockService.searchStocksByName(name, paginationRequestDto, sortParamsDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/markets/{marketName}/securities/search/code")
    public ResponseEntity<String> searchStocksByCode(
            @PathVariable String marketName,
            @RequestParam String code,
            @Valid @ModelAttribute PaginationRequestDto paginationRequestDto,
            @Valid @ModelAttribute SortParamsDto sortParamsDto
    ) {
        Response response = stockService.searchStocksByCode(code, paginationRequestDto, sortParamsDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/markets/{marketName}/securities/code/{code}/data")
    public ResponseEntity<String> getStockByCode(
            @PathVariable String marketName,
            @PathVariable String code,
            @Valid @ModelAttribute GetStockDataDto getStockDataDto
    ) {
        Response response = stockService.getStockByCode(marketName, code, getStockDataDto);
        return grpcResponseHelper.createJsonResponse(response);
    }
}
