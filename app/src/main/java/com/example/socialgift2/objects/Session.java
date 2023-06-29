package com.example.socialgift2.objects;

public class Session {
    public static String userEmail;
    public static String userPass;
    public static String userToken;

    public Session() {
    }
    public Session(String userEmail, String userPass, String userToken) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userToken = userToken;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        Session.userEmail = userEmail;
    }

    public static String getUserPass() {
        return userPass;
    }

    public static void setUserPass(String userPass) {
        Session.userPass = userPass;
    }

    public static String getUserToken() {
        return userToken;
    }

    public static void setUserToken(String userToken) {
        Session.userToken = userToken;
    }
}
