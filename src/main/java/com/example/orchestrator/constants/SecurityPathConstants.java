package com.example.orchestrator.constants;
import java.util.List;

public class SecurityPathConstants {
    private static final String AUTH_LOGIN = "/auth/login";
    private static final String AUTH_LOGOUT = "/auth/logout";
    private static final String AUTH_REGISTER = "/auth/register";
    private static final String USER_CHECK_USERNAME = "/users/check-username";
    private static final String USER_REGISTER = "/users/register";
    private static final String EMAIL_SEND = "/email/send";
    private static final String EMAIL_VERIFY = "/email/verify";
    private static final String STOCK_WILDCARD = "/stock/**";
    private static final String HEALTH_CHECK = "/healthz";
    private static final String READINESS_CHECK = "/readyz";

    public static List<String> getPublicPaths() {
        return List.of(
                AUTH_LOGIN,
                AUTH_REGISTER,
                USER_CHECK_USERNAME,
                USER_REGISTER,
                EMAIL_SEND,
                EMAIL_VERIFY,
                STOCK_WILDCARD,
                HEALTH_CHECK,
                READINESS_CHECK
        );
    }
}
