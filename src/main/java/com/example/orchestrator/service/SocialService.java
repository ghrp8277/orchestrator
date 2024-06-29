package com.example.orchestrator.service;

import com.example.grpc.*;
import com.example.orchestrator.dto.*;
import com.example.orchestrator.service.grpc.SocialGrpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialService {
    private final SocialGrpcService socialGrpcService;

    public Response followUser(FollowUserDto followUserDto) {
        FollowUserRequest followUserRequest = FollowUserRequest.newBuilder()
                .setFollowerId(followUserDto.getFollowerId())
                .setFolloweeId(followUserDto.getFolloweeId())
                .build();

        return socialGrpcService.followUser(followUserRequest);
    }

    public Response unfollowUser(UnFollowUserDto unFollowUserDto) {
        UnfollowRequest unfollowRequest = UnfollowRequest.newBuilder()
                .setFollowerId(unFollowUserDto.getFollowerId())
                .setFolloweeId(unFollowUserDto.getFolloweeId())
                .build();

        return socialGrpcService.unfollowUser(unfollowRequest);
    }

    public Response getPostById(Long postId) {
        GetPostByIdRequest getPostByIdRequest = GetPostByIdRequest.newBuilder()
                .setPostId(postId)
                .build();

        return socialGrpcService.getPostById(getPostByIdRequest);
    }

    public Response getPosts(PaginationRequestDto paginationRequest) {
        GetPostsRequest getPostsRequest = GetPostsRequest.newBuilder()
                .setPage(paginationRequest.getPage())
                .setPageSize(paginationRequest.getPageSize())
                .build();

        return socialGrpcService.getPosts(getPostsRequest);
    }

    public Response createPost(CreatePostDto createPostDto) {
        CreatePostRequest createPostRequest = CreatePostRequest.newBuilder()
                .setUserId(createPostDto.getUserId())
                .setUserId(createPostDto.getUserId())
                .setTitle(createPostDto.getTitle())
                .setAuthor(createPostDto.getAuthor())
                .setAccountName(createPostDto.getAccountName())
                .setContent(createPostDto.getContent())
                .build();

        return socialGrpcService.createPost(createPostRequest);
    }

    public Response updatePost(Long postId, UpdatePostDto updatePostDto) {
        UpdatePostRequest updatePostRequest = UpdatePostRequest.newBuilder()
                .setPostId(postId)
                .setUserId(updatePostDto.getUserId())
                .setTitle(updatePostDto.getTitle())
                .setContent(updatePostDto.getContent())
                .build();

        return socialGrpcService.updatePost(updatePostRequest);
    }

    public Response deletePost(Long postId, DeletePostDto deletePostDto) {
        DeletePostRequest deletePostRequest = DeletePostRequest.newBuilder()
                .setPostId(postId)
                .setUserId(deletePostDto.getUserId())
                .build();

        return socialGrpcService.deletePost(deletePostRequest);
    }

    public Response incrementPostLikes(Long postId, LikeDto likeDto) {
        IncrementPostLikesRequest incrementPostLikesRequest = IncrementPostLikesRequest.newBuilder()
                .setPostId(postId)
                .setUserId(likeDto.getUserId())
                .build();

        return socialGrpcService.incrementPostLikes(incrementPostLikesRequest);
    }

    public Response decrementPostLikes(Long postId, LikeDto likeDto) {
        DecrementPostLikesRequest decrementPostLikesRequest = DecrementPostLikesRequest.newBuilder()
                .setPostId(postId)
                .setUserId(likeDto.getUserId())
                .build();

        return socialGrpcService.decrementPostLikes(decrementPostLikesRequest);
    }

    public Response createComment(Long postId, CreateCommentDto createCommentDto) {
        CreateCommentRequest createCommentRequest = CreateCommentRequest.newBuilder()
                .setPostId(postId)
                .setUserId(createCommentDto.getUserId())
                .setContent(createCommentDto.getContent())
                .build();

        return socialGrpcService.createComment(createCommentRequest);
    }

    public Response updateComment(Long commentId, UpdateCommentDto updateCommentDto) {
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(updateCommentDto.getUserId())
                .setContent(updateCommentDto.getContent())
                .build();

        return socialGrpcService.updateComment(updateCommentRequest);
    }

    public Response deleteComment(Long commentId, DeleteCommentDto deleteCommentDto) {
        DeleteCommentRequest deleteCommentRequest = DeleteCommentRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(deleteCommentDto.getUserId())
                .build();

        return socialGrpcService.deleteComment(deleteCommentRequest);
    }

    public Response createReply(Long postId, Long parentCommentId, CreateReplyDto createReplyDto) {
        CreateReplyRequest createReplyRequest = CreateReplyRequest.newBuilder()
                .setPostId(postId)
                .setUserId(createReplyDto.getUserId())
                .setParentCommentId(parentCommentId)
                .setContent(createReplyDto.getContent())
                .build();

        return socialGrpcService.createReply(createReplyRequest);
    }

    public Response incrementCommentLikes(Long commentId, LikeDto likeDto) {
        IncrementCommentLikesRequest request = IncrementCommentLikesRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(likeDto.getUserId())
                .build();

        return socialGrpcService.incrementCommentLikes(request);
    }

    public Response decrementCommentLikes(Long commentId, LikeDto likeDto) {
        DecrementCommentLikesRequest request = DecrementCommentLikesRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(likeDto.getUserId())
                .build();

        return socialGrpcService.decrementCommentLikes(request);
    }
}
