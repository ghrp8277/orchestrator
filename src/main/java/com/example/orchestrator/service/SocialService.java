package com.example.orchestrator.service;

import com.example.grpc.*;
import com.example.orchestrator.controller.WebSocketController;
import com.example.orchestrator.dto.*;
import com.example.orchestrator.service.grpc.SocialGrpcService;
import com.example.orchestrator.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocialService {
    private final SocialGrpcService socialGrpcService;
    private final WebSocketController webSocketController;
    private final JsonUtil jsonUtil;

    public Response followUser(FollowUserDto followUserDto) {
        FollowUserRequest followUserRequest = FollowUserRequest.newBuilder()
                .setFollowerId(followUserDto.getFollowerId())
                .setFolloweeId(followUserDto.getFolloweeId())
                .build();

        Response response = socialGrpcService.followUser(followUserRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(followUserDto.getFolloweeId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response unfollowUser(UnFollowUserDto unFollowUserDto) {
        UnfollowRequest unfollowRequest = UnfollowRequest.newBuilder()
                .setFollowerId(unFollowUserDto.getFollowerId())
                .setFolloweeId(unFollowUserDto.getFolloweeId())
                .build();

        Response response = socialGrpcService.unfollowUser(unfollowRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(unFollowUserDto.getFolloweeId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public List<Long> getFollowers(Long userId) {
        GetFollowersRequest getFollowersRequest = GetFollowersRequest.newBuilder()
                .setUserId(userId)
                .build();

        Response response = socialGrpcService.getFollowers(getFollowersRequest);
        String result = response.getResult();
        Map<String, Object> followers = jsonUtil.getMapByKey(result, "followers");
        List<Integer> followerIds = (List<Integer>) followers.get("followerIds");

        return followerIds.stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());
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
                .setStockCode(createPostDto.getStockCode())
                .build();

        Response response = socialGrpcService.createPost(createPostRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(createPostDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response updatePost(Long postId, UpdatePostDto updatePostDto) {
        UpdatePostRequest updatePostRequest = UpdatePostRequest.newBuilder()
                .setPostId(postId)
                .setUserId(updatePostDto.getUserId())
                .setTitle(updatePostDto.getTitle())
                .setContent(updatePostDto.getContent())
                .build();

        Response response = socialGrpcService.updatePost(updatePostRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(updatePostDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response deletePost(Long postId, DeletePostDto deletePostDto) {
        DeletePostRequest deletePostRequest = DeletePostRequest.newBuilder()
                .setPostId(postId)
                .setUserId(deletePostDto.getUserId())
                .build();

        Response response = socialGrpcService.deletePost(deletePostRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(deletePostDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response incrementPostLikes(Long postId, LikeDto likeDto) {
        IncrementPostLikesRequest incrementPostLikesRequest = IncrementPostLikesRequest.newBuilder()
                .setPostId(postId)
                .setUserId(likeDto.getUserId())
                .build();

        Response response = socialGrpcService.incrementPostLikes(incrementPostLikesRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(likeDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response decrementPostLikes(Long postId, LikeDto likeDto) {
        DecrementPostLikesRequest decrementPostLikesRequest = DecrementPostLikesRequest.newBuilder()
                .setPostId(postId)
                .setUserId(likeDto.getUserId())
                .build();

        Response response = socialGrpcService.decrementPostLikes(decrementPostLikesRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(likeDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response createComment(Long postId, CreateCommentDto createCommentDto) {
        CreateCommentRequest createCommentRequest = CreateCommentRequest.newBuilder()
                .setPostId(postId)
                .setUserId(createCommentDto.getUserId())
                .setContent(createCommentDto.getContent())
                .build();

        Response response = socialGrpcService.createComment(createCommentRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(createCommentDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response updateComment(Long commentId, UpdateCommentDto updateCommentDto) {
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(updateCommentDto.getUserId())
                .setContent(updateCommentDto.getContent())
                .build();

        Response response = socialGrpcService.updateComment(updateCommentRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(updateCommentDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response deleteComment(Long commentId, DeleteCommentDto deleteCommentDto) {
        DeleteCommentRequest deleteCommentRequest = DeleteCommentRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(deleteCommentDto.getUserId())
                .build();

        Response response = socialGrpcService.deleteComment(deleteCommentRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(deleteCommentDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response createReply(Long postId, Long parentCommentId, CreateReplyDto createReplyDto) {
        CreateReplyRequest createReplyRequest = CreateReplyRequest.newBuilder()
                .setPostId(postId)
                .setUserId(createReplyDto.getUserId())
                .setParentCommentId(parentCommentId)
                .setContent(createReplyDto.getContent())
                .build();

        Response response = socialGrpcService.createReply(createReplyRequest);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(createReplyDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response incrementCommentLikes(Long commentId, LikeDto likeDto) {
        IncrementCommentLikesRequest request = IncrementCommentLikesRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(likeDto.getUserId())
                .build();

        Response response = socialGrpcService.incrementCommentLikes(request);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(likeDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response decrementCommentLikes(Long commentId, LikeDto likeDto) {
        DecrementCommentLikesRequest request = DecrementCommentLikesRequest.newBuilder()
                .setCommentId(commentId)
                .setUserId(likeDto.getUserId())
                .build();

        Response response = socialGrpcService.decrementCommentLikes(request);
        if (!responseHasError(response)) {
            List<Long> followers = getFollowers(likeDto.getUserId());
            webSocketController.sendActivityUpdate(followers, this::getLatestActivityForFollowees);
        }
        return response;
    }

    public Response getUnreadFeedActivities(Long userId, PaginationRequestDto paginationRequestDto) {
        GetUnreadFeedActivitiesRequest request = GetUnreadFeedActivitiesRequest.newBuilder()
                .setUserId(userId)
                .setPage(paginationRequestDto.getPage())
                .setPageSize(paginationRequestDto.getPageSize())
                .build();

        return socialGrpcService.getUnreadFeedActivities(request);
    }

    public Response getFeedActivities(Long userId, PaginationRequestDto paginationRequestDto) {
        GetFeedActivitiesRequest request = GetFeedActivitiesRequest.newBuilder()
                .setUserId(userId)
                .setPage(paginationRequestDto.getPage())
                .setPageSize(paginationRequestDto.getPageSize())
                .build();

        return socialGrpcService.getFeedActivities(request);
    }

    public Response markActivityAsRead(Long activityId) {
        MarkActivityAsReadRequest request = MarkActivityAsReadRequest.newBuilder()
                .setActivityId(activityId)
                .build();

        return socialGrpcService.markActivityAsRead(request);
    }

    public Response getLatestActivityForFollowees(Long userId) {
        GetLatestActivityRequest request = GetLatestActivityRequest.newBuilder()
                .setUserId(userId)
                .build();

        return socialGrpcService.getLatestActivityForFollowees(request);
    }

    private boolean responseHasError(Response response) {
        String result = response.getResult();
        return result.contains("\"error\"");
    }
}
