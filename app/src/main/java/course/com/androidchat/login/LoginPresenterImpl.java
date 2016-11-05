package course.com.androidchat.login;

import android.util.Log;

import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;
import course.com.androidchat.login.events.LoginEvent;
import course.com.androidchat.login.ui.LoginView;
import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        disableInputsInView();
        loginInteractor.checkSession();
    }

    private void disableInputsInView() {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
    }

    private void enableInputsInView() {
        if (loginView != null) {
            loginView.enableInputs();
            loginView.hideProgress();
        }
    }

    @Override
    public void validateLogin(String email, String password) {
        disableInputsInView();
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        disableInputsInView();
        loginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.ON_SIGN_IN_SUCCESS:
                onSignInSuccess();
                break;
            case LoginEvent.ON_SIGN_UP_SUCCESS:
                onSignUpSuccess();
                break;
            case LoginEvent.ON_SIGN_IN_ERROR:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.ON_SIGN_UP_ERROR:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.ON_RECOVER_SESSION_ERROR:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        enableInputsInView();
        Log.e("LoginPresenterImpl", "onFailedToRecoverSession");
    }

    private void onSignInSuccess() {
        if (loginView!=null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess() {
        if (loginView!=null) {
            loginView.newUserSuccess();
        }
    }

    private void onSignInError(String errorMsg) {
        if (loginView!=null) {
            enableInputsInView();
            loginView.showLoginError(errorMsg);
        }
    }

    private void onSignUpError(String errorMsg) {
        if (loginView!=null) {
            enableInputsInView();
            loginView.newUserError(errorMsg);
        }
    }
}
