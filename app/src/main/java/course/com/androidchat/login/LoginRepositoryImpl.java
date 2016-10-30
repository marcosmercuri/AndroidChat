package course.com.androidchat.login;

import android.util.Log;

import course.com.androidchat.helper.FirebaseHelper;

public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseHelper firebaseHelper;

    public LoginRepositoryImpl() {
        this.firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void checkSession() {
        Log.e("LoginRepositoryImpl", "checkSession");
    }

    @Override
    public void doSignIn(String email, String password) {
        Log.e("LoginRepositoryImpl", "signIn");
    }

    @Override
    public void doSignUp(String email, String password) {
        Log.e("LoginRepositoryImpl", "signUp");
    }
}
