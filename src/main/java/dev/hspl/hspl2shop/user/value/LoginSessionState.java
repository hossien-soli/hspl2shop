package dev.hspl.hspl2shop.user.value;

public enum LoginSessionState {
    ACTIVE,
    LOGGED_OUT, // user logout
    EXPIRED, // expiration of the last(non-refreshed) refresh token of the session or family
    INVALIDATED // refresh token reuse detection!!! or admin manual invalidation!
}
