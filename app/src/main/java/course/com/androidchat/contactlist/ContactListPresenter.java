package course.com.androidchat.contactlist;

import course.com.androidchat.contactlist.event.ContactListEvent;

public interface ContactListPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);

}
