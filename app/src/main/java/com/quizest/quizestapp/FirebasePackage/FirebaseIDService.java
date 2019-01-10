package com.quizest.quizestapp.FirebasePackage;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.quizest.quizestapp.LocalStorage.Storage;


public class FirebaseIDService  extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        saveToken(token);
    }

    private void saveToken(String token){
        Storage storage = new Storage(this);
        storage.SaveFirebaseToken(token);
    }
}
