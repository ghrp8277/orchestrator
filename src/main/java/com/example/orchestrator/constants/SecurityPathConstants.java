package com.example.orchestrator.constants;
import java.util.List;

public class SecurityPathConstants {
    private static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_LOGOUT = "/auth/logout";
    private static final String AUTH_REGISTER = "/auth/register";
    private static final String USER_CHECK_USERNAME = "/users/check-username";
    private static final String USER_REGISTER = "/users/register";
    public static final String USER_INFO = "/users/*";
    private static final String EMAIL_SEND = "/email/send";
    private static final String EMAIL_VERIFY = "/email/verify";
    public static final String SOCIAL_POSTS = "/social/posts/symbol/*";
    public static final String SOCIAL_DETAIL_POSTS = "/social/posts/detail/*";
    private static final String SOCIAL_UNFOLLOWED_USERS = "/social/unfollowed-users";
    private static final String SOCIAL_UNREAD_ACTIVITIES = "/social/activities/unread";
    public static final String SOCIAL_PUBLIC_DETAIL_POSTS = "/social/posts/public/detail/*";
    private static final String STOCK_WILDCARD = "/stock/**";
    private static final String PRODUCT_POINTS = "/product/points";
    private static final String TEST = "/wallet/create";
    private static final String HEALTH_CHECK = "/healthz";
    private static final String READINESS_CHECK = "/readyz";

    public static List<String> getPublicPaths() {
        return List.of(
                TEST,
                AUTH_LOGIN,
                AUTH_REGISTER,
                USER_CHECK_USERNAME,
                USER_REGISTER,
                EMAIL_SEND,
                EMAIL_VERIFY,
                SOCIAL_UNFOLLOWED_USERS,
                STOCK_WILDCARD,
                PRODUCT_POINTS,
                HEALTH_CHECK,
                READINESS_CHECK,
                SOCIAL_UNREAD_ACTIVITIES
        );
    }
}
