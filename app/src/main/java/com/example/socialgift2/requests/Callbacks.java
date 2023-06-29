package com.example.socialgift2.requests;

import java.util.List;

public interface Callbacks {

    interface UserCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
    interface UserCallbackLogin {
        void onSuccess(String token);
        void onError(String errorMessage);
    }
    interface UserCallbackSearch<User> {
        void onSuccess(List<User> userList);
        void onError(String errorMessage);
    }
}
