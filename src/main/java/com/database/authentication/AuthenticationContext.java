package com.database.authentication;

public class AuthenticationContext implements IAuthenticationContext {
    private static UserAuthStrategy authStrategy;
    public static AuthenticationContext instance;

    private AuthenticationContext(){}

    public static AuthenticationContext getInstance() {
        if (instance == null) {
            instance = new AuthenticationContext();
        }
        return instance;
    }

    public void setAuthStrategy(UserAuthStrategy userAuthStrategy) {
        authStrategy = userAuthStrategy;
    }

    public String  signIn(Object user) {
        return authStrategy.signIn(user);
    }

    public void signUp(Object user) {
        authStrategy.signUp(user);
    }

    public void deactivateAccount(Integer id) {
        authStrategy.deactivate(id);
    }

    public void updatePassword(int id, String password){
        authStrategy.updatePassword(id, password);
    }
}
