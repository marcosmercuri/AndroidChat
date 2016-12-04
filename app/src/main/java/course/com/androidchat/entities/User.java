package course.com.androidchat.entities;

import java.util.HashMap;
import java.util.Map;

public class User {
    public static final Boolean ONLINE = true;
    public static final Boolean OFFLINE = false;

    private final String email;
    private Boolean isOnline;
    private Map<String, Boolean> contactsStatus;

    public User() {
        this(null);
    }

    public User(String email) {
        this(email, false);
    }

    public User(String email, Boolean isOnline) {
        this.email = email;
        this.isOnline = isOnline;
        this.contactsStatus = new HashMap<>();
    }

    public String getEmail() {
        return email;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        this.isOnline = online;
    }

    public Map<String, Boolean> getContactsStatus() {
        return contactsStatus;
    }

    public void setContactsStatus(Map<String, Boolean> contactsStatus) {
        this.contactsStatus = contactsStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
