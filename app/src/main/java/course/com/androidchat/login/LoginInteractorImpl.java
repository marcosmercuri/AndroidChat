package course.com.androidchat.login;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginRepository loginRepository;

    public LoginInteractorImpl() {
        this.loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        loginRepository.checkSession();
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.doSignIn(email, password);
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.doSignUp(email, password);
    }
}
