package com.example.orchestrator.controller;

import com.example.grpc.Response;
import com.example.orchestrator.constants.HttpConstants;
import com.example.orchestrator.service.ProductService;
import com.example.orchestrator.util.GrpcResponseHelper;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final GrpcResponseHelper grpcResponseHelper;

    @Autowired
    public ProductController(ProductService productService, GrpcResponseHelper grpcResponseHelper) {
        this.productService = productService;
        this.grpcResponseHelper = grpcResponseHelper;
    }

    @GetMapping(value = "/points")
    public ResponseEntity<String> getAllPoints() {
        Response response = productService.getAllPoints();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(HttpConstants.CACHE_MAX_AGE_SECONDS, TimeUnit.SECONDS))
                .body(grpcResponseHelper.createJsonResponse(response).getBody());
    }
}
