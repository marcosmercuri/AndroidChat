package course.com.androidchat.addcontact.events;

public class AddContactEvent {
    private Boolean error;

    public AddContactEvent() {
        this.error = false;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
