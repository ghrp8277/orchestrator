package com.example.orchestrator.service.grpc;

import com.example.grpc.WalletServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

@Service
public class WalletGrpcServiceImpl implements WalletGrpcService {
    private final WalletServiceGrpc.WalletServiceBlockingStub walletServiceBlockingStub;
    private final ManagedChannel channel;

    public WalletGrpcServiceImpl(@Value("${wallet.grpc.host}") String grpcHost, @Value("${wallet.grpc.port}") int grpcPort) {
        this.channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
        this.walletServiceBlockingStub = WalletServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public Response createWallet(CreateWalletRequest createWalletRequest) {
        return walletServiceBlockingStub.createWallet(createWalletRequest);
    }

    @Override
    public Response deposit(DepositRequest depositRequest) {
        return walletServiceBlockingStub.deposit(depositRequest);
    }

    @Override
    public Response withdraw(WithdrawRequest withdrawRequest) {
        return walletServiceBlockingStub.withdraw(withdrawRequest);
    }
}
