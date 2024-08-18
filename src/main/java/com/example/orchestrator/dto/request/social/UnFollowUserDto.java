package com.example.orchestrator.dto.request.social;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UnFollowUserDto {

    @NotNull(message = "Follower ID is required")
    private Long followerId;

    @NotNull(message = "Followee ID is required")
    private Long followeeId;
}
