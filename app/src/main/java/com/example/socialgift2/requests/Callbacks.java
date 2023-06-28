package com.example.socialgift2.requests;

public interface Callbacks {

    interface DataManagerCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
}
