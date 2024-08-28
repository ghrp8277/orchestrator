package com.example.orchestrator.service;

import com.example.orchestrator.dto.MovingAveragePeriodsDto;
import com.example.orchestrator.dto.request.common.PaginationRequestDto;
import com.example.orchestrator.dto.request.stock.FavoriteRequestDto;
import com.example.orchestrator.dto.request.stock.SortParamsDto;
import com.example.orchestrator.dto.request.common.TimeframeRequestDto;
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
            TimeframeRequestDto timeframeRequestDto
    ) {
        GetStockDataByMarketAndCodeRequest getStockDataByMarketAndCodeRequest = GetStockDataByMarketAndCodeRequest.newBuilder()
                .setMarketName(marketName)
                .setCode(code)
                .setTimeframe(timeframeRequestDto.getTimeframe())
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

    public Response getMovingAverages(
            String code,
            MovingAveragePeriodsDto movingAveragePeriodsDto,
            TimeframeRequestDto timeframeRequestDto
    ) {
        List<Integer> periodsList = movingAveragePeriodsDto.getPeriods();

        GetMovingAveragesRequest.Builder requestBuilder = GetMovingAveragesRequest.newBuilder()
            .setStockCode(code)
            .setTimeframe(timeframeRequestDto.getTimeframe());

        requestBuilder.addAllPeriods(periodsList);
        GetMovingAveragesRequest getMovingAveragesRequest = requestBuilder.build();
        return stockGrpcService.getMovingAverages(getMovingAveragesRequest);
    }

    public Response getBollingerBands(
            String code,
            TimeframeRequestDto timeframeRequestDto
    ) {
        GetBollingerBandsRequest getBollingerBandsRequest = GetBollingerBandsRequest.newBuilder()
                .setStockCode(code)
                .setTimeframe(timeframeRequestDto.getTimeframe())
                .build();
        return stockGrpcService.getBollingerBands(getBollingerBandsRequest);
    }

    public Response getMACD(
            String code,
            TimeframeRequestDto timeframeRequestDto
    ) {
        GetMACDRequest getMACDRequest = GetMACDRequest.newBuilder()
                .setStockCode(code)
                .setTimeframe(timeframeRequestDto.getTimeframe())
                .build();
        return stockGrpcService.getMACD(getMACDRequest);
    }

    public Response getRSI(
            String code,
            TimeframeRequestDto timeframeRequestDto
    ) {
        GetRSIRequest getRSIRequest = GetRSIRequest.newBuilder()
                .setStockCode(code)
                .setTimeframe(timeframeRequestDto.getTimeframe())
                .build();
        return stockGrpcService.getRSI(getRSIRequest);
    }

    public Response getFavoritesByUser(Long userId, PaginationRequestDto paginationRequest) {
        GetFavoritesByUserRequest request = GetFavoritesByUserRequest.newBuilder()
                .setUserId(userId)
                .setPage(paginationRequest.getPage())
                .setPageSize(paginationRequest.getPageSize())
                .build();
        return stockGrpcService.getFavoritesByUser(request);
    }

    public Response addFavorite(FavoriteRequestDto favoriteRequestDto) {
        AddFavoriteRequest request = AddFavoriteRequest.newBuilder()
                .setUserId(favoriteRequestDto.getUserId())
                .setStockCode(favoriteRequestDto.getStockCode())
                .build();
        return stockGrpcService.addFavorite(request);
    }

    public Response removeFavorite(FavoriteRequestDto favoriteRequestDto) {
        RemoveFavoriteRequest request = RemoveFavoriteRequest.newBuilder()
                .setUserId(favoriteRequestDto.getUserId())
                .setStockCode(favoriteRequestDto.getStockCode())
                .build();
        return stockGrpcService.removeFavorite(request);
    }
}
