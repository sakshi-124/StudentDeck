package com.database.authentication;

public interface UserAuthStrategy {
    public abstract String signIn(Object user);
    public abstract void signUp(Object user);
    public abstract void deactivate(Integer userId);
    public abstract void updatePassword(int id, String updatedPassword);
}
