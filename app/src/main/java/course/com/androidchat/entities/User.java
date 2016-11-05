package course.com.androidchat.entities;

import java.util.HashMap;
import java.util.Map;

public class User {
    public static final Boolean ONLINE = true;
    public static final Boolean OFFLINE = false;

    private final String email;
    private Boolean online;
    private Map<String, Boolean> contactsStatus;

    public User() {
        this(null);
    }

    public User(String email) {
        this.email = email;
        this.online = false;
        this.contactsStatus = new HashMap<>();
    }

    public User(String email, Boolean online) {
        this.email = email;
        this.online = online;
        this.contactsStatus = new HashMap<>();
    }

    public String getEmail() {
        return email;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Map<String, Boolean> getContactsStatus() {
        return contactsStatus;
    }

    public void setContactsStatus(Map<String, Boolean> contactsStatus) {
        this.contactsStatus = contactsStatus;
    }
}
