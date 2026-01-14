package service.auth;

import model.User;

public class LoginResult {

    private User user;
    private AuthStatus authStatus;

    public LoginResult() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthStatus getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }
}
