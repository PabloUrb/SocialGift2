package com.example.socialgift2.objects;

public class Session {
    public static User user = new User();
    public static String token;

    public Session() {
    }
    public Session(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Session.token = token;
    }
}
