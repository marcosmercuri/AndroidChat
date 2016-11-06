package course.com.androidchat.contactlist;

public interface ContactListInteractor {
    void subscribe();
    void unsubscribe();
    void removeContact(String email);
    void destroyListener();



}
