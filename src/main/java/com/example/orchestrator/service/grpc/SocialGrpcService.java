package com.example.orchestrator.service.grpc;

import com.example.grpc.*;

public interface SocialGrpcService {
    Response followUser(FollowUserRequest followUserRequest);
    Response unfollowUser(UnfollowRequest unfollowRequest);
    Response getPostById(GetPostByIdRequest getPostByIdRequest);
    Response getPosts(GetPostsRequest getPostsRequest);
    Response createPost(CreatePostRequest createPostRequest);
    Response updatePost(UpdatePostRequest updatePostRequest);
    Response deletePost(DeletePostRequest deletePostRequest);
    Response createComment(CreateCommentRequest createCommentRequest);
    Response updateComment(UpdateCommentRequest updateCommentRequest);
    Response deleteComment(DeleteCommentRequest deleteCommentRequest);
    Response createReply(CreateReplyRequest createReplyRequest);
    Response incrementCommentLikes(IncrementCommentLikesRequest incrementCommentLikesRequest);
    Response decrementCommentLikes(DecrementCommentLikesRequest decrementCommentLikesRequest);
    Response incrementPostLikes(IncrementPostLikesRequest incrementPostLikesRequest);
    Response decrementPostLikes(DecrementPostLikesRequest decrementPostLikesRequest);
}
