package course.com.androidchat.login;

import android.util.Log;

import course.com.androidchat.helper.FirebaseHelper;
import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;
import course.com.androidchat.login.events.LoginEvent;

public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseHelper firebaseHelper;

    public LoginRepositoryImpl() {
        this.firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void checkSession() {
        postEvent(LoginEvent.ON_RECOVER_SESSION_ERROR);
    }

    @Override
    public void doSignIn(String email, String password) {
        postEvent(LoginEvent.ON_SIGN_IN_SUCCESS);
    }

    @Override
    public void doSignUp(String email, String password) {
        postEvent(LoginEvent.ON_SIGN_UP_SUCCESS);
    }

    private void postEvent(int eventType, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(eventType);
        if (errorMessage!=null) {
            loginEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int eventType) {
        postEvent(eventType, null);
    }
}
