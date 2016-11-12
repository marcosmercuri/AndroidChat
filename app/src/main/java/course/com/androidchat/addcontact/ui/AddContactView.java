package course.com.androidchat.addcontact.ui;

public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgressBar();
    void hideProgressBar();
    void contactAdded();
    void contactNotAdded();
}
