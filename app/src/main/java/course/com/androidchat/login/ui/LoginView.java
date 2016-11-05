package course.com.androidchat.login.ui;

public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignIn();
    void handleSignUp();

    void navigateToMainScreen();
    void showLoginError(String error);

    void newUserSuccess();
    void newUserError(String error);
}
