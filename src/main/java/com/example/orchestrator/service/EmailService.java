package com.example.orchestrator.service;

import com.example.grpc.*;
import com.example.orchestrator.dto.request.email.EmailSendDto;
import com.example.orchestrator.dto.request.email.EmailVerifyCodeDto;
import com.example.orchestrator.service.grpc.EmailGrpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailGrpcService emailGrpcService;

    public Response emailSend(EmailSendDto emailSendDto) {
        EmailSendRequest request = EmailSendRequest.newBuilder()
                .setEmail(emailSendDto.getEmail())
                .build();

        return emailGrpcService.emailSend(request);
    }

    public Response verifyEmailCode(EmailVerifyCodeDto emailVerifyCodeDto) {
        VerifyEmailCodeRequest request = VerifyEmailCodeRequest.newBuilder()
                .setEmail(emailVerifyCodeDto.getEmail())
                .setCode(emailVerifyCodeDto.getCode())
                .build();

        return emailGrpcService.verifyEmailCode(request);
    }
}
