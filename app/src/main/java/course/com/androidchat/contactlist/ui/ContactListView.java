package course.com.androidchat.contactlist.ui;

import course.com.androidchat.entities.User;

public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
