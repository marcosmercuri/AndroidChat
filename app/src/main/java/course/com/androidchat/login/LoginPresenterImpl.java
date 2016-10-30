package course.com.androidchat.login;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onDestroy() {
        loginView = null;
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
