package course.com.androidchat.login;

public interface LoginInteractor {
    void checkSession();
    void doSignIn(String email, String password);
    void doSignUp(String email, String password);
}
