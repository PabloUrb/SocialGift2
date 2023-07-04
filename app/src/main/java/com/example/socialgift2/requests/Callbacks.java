package com.example.socialgift2.requests;

import java.util.List;

public interface Callbacks {

    interface UserCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
    public interface CallbacksCount<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }
    interface UserCallbackLogin {
        void onSuccess(String token);
        void onError(String errorMessage);
    }
    interface UserCallbackSearch<User> {
        void onSuccess(List<User> lstUsers);
        void onError(String errorMessage);
    }
    interface UserCallbackWishlists<Wishlist> {
        void onSuccess(List<Wishlist> lstWishlist);

        void onError(String errorMessage);
    }
    interface UserCallbackGift<Gift> {
        void onSuccess(List<Gift> lstGift);
        void onError(String errorMessage);
    }
    interface UserCallbackProduct<Product> {
        void onSuccess(Product product);

        void onError(String errorMessage);
    }
    interface UserCallbackProductList<Product> {
        List<com.example.socialgift2.objects.Product> onSuccess(List<Product> products);

        void onError(String errorMessage);
    }
    interface UserCallbackUsersList<User> {
        void onSuccess(List<User> userList);
        void onError(String errorMessage);
    }
}
