package course.com.androidchat.login.events;

// TODO Can this be refactored?
public class LoginEvent {
    public static final int ON_SIGN_IN_ERROR = 0;
    public static final int ON_SIGN_UP_ERROR = 1;
    public static final int ON_SIGN_IN_SUCCESS = 2;
    public static final int ON_SIGN_UP_SUCCESS = 3;
    public static final int ON_RECOVER_SESSION_ERROR = 4;

    private int eventType;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
