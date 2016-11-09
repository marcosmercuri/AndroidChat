package course.com.androidchat.contactlist.event;

import course.com.androidchat.entities.User;

public class ContactListEvent {
    public static final int ON_CONTACT_ADDED = 0;
    public static final int ON_CONTACT_CHANGED = 1;
    public static final int ON_CONTACT_REMOVED = 2;

    private User user;
    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
