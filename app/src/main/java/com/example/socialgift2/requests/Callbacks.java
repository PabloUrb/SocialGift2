package com.example.socialgift2.requests;

public interface Callbacks {

    interface DataManagerCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
    interface DataManagerCallbackLogin {
        void onSuccess(String token);
        void onError(String errorMessage);
    }
}
