package course.com.androidchat.contactlist;

public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void subscribeToContactListEvent();
    void unsubscribeToContactListEvent();
    void destroyListener();
    void changeConnectionStatus(Boolean online);

}
