package com.example.orchestrator.controller;

import com.example.grpc.Response;
import com.example.orchestrator.dto.*;
import com.example.orchestrator.dto.request.common.PaginationRequestDto;
import com.example.orchestrator.dto.request.social.*;
import com.example.orchestrator.service.SocialService;
import com.example.orchestrator.util.GrpcResponseHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social")
public class SocialController {
    private static final Logger logger = LoggerFactory.getLogger(SocialController.class);
    private final SocialService socialService;
    private final GrpcResponseHelper grpcResponseHelper;

    @Autowired
    public SocialController(SocialService socialService, GrpcResponseHelper grpcResponseHelper) {
        this.socialService = socialService;
        this.grpcResponseHelper = grpcResponseHelper;
    }

    @PostMapping(value = "/follow", consumes = "application/json")
    public ResponseEntity<String> followUser(@Valid @RequestBody FollowUserDto followUserDto) {
        Response response = socialService.followUser(followUserDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/unfollow", consumes = "application/json")
    public ResponseEntity<String> unfollowUser(@Valid @RequestBody UnFollowUserDto unFollowUserDto) {
        Response response = socialService.unfollowUser(unFollowUserDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/posts", consumes = "application/json")
    public ResponseEntity<String> createPost(@Valid @RequestBody CreatePostDto createPostDto) {
        Response response = socialService.createPost(createPostDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PutMapping(value = "/posts/{postId}", consumes = "application/json")
    public ResponseEntity<String> updatePost(@PathVariable @NotNull Long postId, @Valid @RequestBody UpdatePostDto updatePostDto) {
        Response response = socialService.updatePost(postId, updatePostDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @DeleteMapping(value = "/posts/{postId}", consumes = "application/json")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @Valid @RequestBody DeletePostDto deletePostDto) {
        Response response = socialService.deletePost(postId, deletePostDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/posts/detail/{postId}")
    public ResponseEntity<String> getPostById(
            @PathVariable @NotNull @Positive Long postId,
            @RequestHeader("user-id") @NotNull Long userId
    ) {
        Response response = socialService.getPostById(postId, userId);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/posts/public/detail/{postId}")
    public ResponseEntity<String> getPostByIdAndNotLogin(
            @PathVariable @NotNull @Positive Long postId
    ) {
        Response response = socialService.getPostByIdAndNotLogin(postId);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/posts/symbol/{code}")
    public ResponseEntity<String> getPosts(
            @PathVariable @NotBlank String code,
            @Valid @ModelAttribute PaginationRequestDto paginationRequest
    ) {
        Response response = socialService.getPosts(code, paginationRequest);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<String> searchPosts(@Valid @ModelAttribute SearchPostsRequestDto searchRequest) {
        Response response = socialService.searchPosts(searchRequest);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/posts/{postId}/like", consumes = "application/json")
    public ResponseEntity<String> incrementPostLikes(@PathVariable Long postId, @Valid @RequestBody LikeDto likeDto) {
        Response response = socialService.incrementPostLikes(postId, likeDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/posts/{postId}/unlike", consumes = "application/json")
    public ResponseEntity<String> decrementPostLikes(@PathVariable Long postId, @Valid @RequestBody LikeDto likeDto) {
        Response response = socialService.decrementPostLikes(postId, likeDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/posts/{postId}/comments", consumes = "application/json")
    public ResponseEntity<String> createComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentDto createCommentDto) {
        Response response = socialService.createComment(postId, createCommentDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PutMapping(value = "/comments/{commentId}", consumes = "application/json")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentDto updateCommentDto) {
        Response response = socialService.updateComment(commentId, updateCommentDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @DeleteMapping(value = "/comments/{commentId}", consumes = "application/json")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @Valid @RequestBody DeleteCommentDto deleteCommentDto) {
        Response response = socialService.deleteComment(commentId, deleteCommentDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/posts/{postId}/comments/{parentCommentId}/reply", consumes = "application/json")
    public ResponseEntity<String> createReply(@PathVariable Long postId, @PathVariable Long parentCommentId, @Valid @RequestBody CreateReplyDto createReplyDto) {
        Response response = socialService.createReply(postId, parentCommentId, createReplyDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/comments/{commentId}/like", consumes = "application/json")
    public ResponseEntity<String> incrementCommentLikes(@PathVariable Long commentId, @Valid @RequestBody LikeDto likeDto) {
        Response response = socialService.incrementCommentLikes(commentId, likeDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/comments/{commentId}/unlike", consumes = "application/json")
    public ResponseEntity<String> decrementCommentLikes(@PathVariable Long commentId, @Valid @RequestBody LikeDto likeDto) {
        Response response = socialService.decrementCommentLikes(commentId, likeDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/activities")
    public ResponseEntity<String> getFeedActivities(@Valid @ModelAttribute PaginationRequestDto paginationRequest, @RequestParam Long userId) {
        Response response = socialService.getFeedActivities(userId, paginationRequest);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/activities/unread")
    public ResponseEntity<String> getUnreadFeedActivities(
            @RequestHeader("user-id") @NotNull Long userId,
            @Valid @ModelAttribute PaginationRequestDto paginationRequest
    ) {
        Response response = socialService.getUnreadFeedActivities(userId, paginationRequest);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping("/activities/{activityId}/mark-read")
    public ResponseEntity<String> markActivityAsRead(@PathVariable Long activityId) {
        Response response = socialService.markActivityAsRead(activityId);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @GetMapping("/unfollowed-users")
    public ResponseEntity<String> getUnfollowedUsers(
            @RequestHeader("user-id") @NotNull Long userId,
            @Valid @ModelAttribute PaginationRequestDto paginationRequest) {
        Response response = socialService.getUnfollowedUsers(userId, paginationRequest);
        return grpcResponseHelper.createJsonResponse(response);
    }
}
