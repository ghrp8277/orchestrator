package com.example.orchestrator.service;

import com.example.orchestrator.dto.GetStockDataDto;
import com.example.orchestrator.dto.PaginationRequestDto;
import com.example.orchestrator.dto.SortParamsDto;
import com.example.orchestrator.service.grpc.StockGrpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockGrpcService stockGrpcService;

    public Response getMarkets() {
        Empty empty = Empty.newBuilder().build();
        return stockGrpcService.getMarkets(empty);
    }

    public Response getStocksByMarket(String marketName) {
        GetStocksByMarketRequest getMarketByNameRequest = GetStocksByMarketRequest.newBuilder()
                .setMarketName(marketName)
                .build();

        return stockGrpcService.getStocksByMarket(getMarketByNameRequest);
    }

    public Response getStockByCode(
            String marketName,
            String code,
            GetStockDataDto getStockDataDto
    ) {
        GetStockDataByMarketAndCodeRequest getStockDataByMarketAndCodeRequest = GetStockDataByMarketAndCodeRequest.newBuilder()
                .setMarketName(marketName)
                .setCode(code)
                .setTimeframe(getStockDataDto.getTimeframe())
                .build();

        return stockGrpcService.getStockDataByMarketAndCode(getStockDataByMarketAndCodeRequest);
    }

    public Response getAllStocksByMarket(
            String marketName,
            PaginationRequestDto paginationRequestDto,
            SortParamsDto sortParamsDto
    ) {
        List<String> sortList = Arrays.asList(sortParamsDto.getSort().split(","));

        GetAllStocksByMarketRequest getAllStocksByMarketRequest = GetAllStocksByMarketRequest.newBuilder()
                .setMarketName(marketName)
                .setPage(paginationRequestDto.getPage())
                .setSize(paginationRequestDto.getPageSize())
                .addAllSort(sortList)
                .build();

        return stockGrpcService.getAllStocksByMarket(getAllStocksByMarketRequest);
    }

    public Response searchStocksByName(
            String name,
            PaginationRequestDto paginationRequestDto,
            SortParamsDto sortParamsDto
    ) {
        List<String> sortList = Arrays.asList(sortParamsDto.getSort().split(","));

        SearchStocksByNameRequest searchStocksByNameRequest = SearchStocksByNameRequest.newBuilder()
                .setName(name)
                .setPage(paginationRequestDto.getPage())
                .setSize(paginationRequestDto.getPageSize())
                .addAllSort(sortList)
                .build();

        return stockGrpcService.searchStocksByName(searchStocksByNameRequest);
    }

    public Response searchStocksByCode(
        String code,
        PaginationRequestDto paginationRequestDto,
        SortParamsDto sortParamsDto
    ) {
        List<String> sortList = Arrays.asList(sortParamsDto.getSort().split(","));

        SearchStocksByCodeRequest searchStocksByCodeRequest = SearchStocksByCodeRequest.newBuilder()
                .setCode(code)
                .setPage(paginationRequestDto.getPage())
                .setSize(paginationRequestDto.getPageSize())
                .addAllSort(sortList)
                .build();

        return stockGrpcService.searchStocksByCode(searchStocksByCodeRequest);
    }
}
