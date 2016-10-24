package course.com.androidchat;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class FirebaseHelper {
    private final static String FIREBASE_URL = "https://console.firebase.google.com/project/android-chat-4b149";
    private final static String USERS_PATH = "users";
    private final static String CHATS_PATH = "chats";
    private final static String CONTACTS_PATH = "contacts";
    private final static String SEPARATOR = "___";
    private Firebase dataReference;

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    private FirebaseHelper() {
        this.dataReference = new Firebase(FIREBASE_URL);
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Firebase getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail() {
        AuthData authData = dataReference.getAuth();
        if (authData!=null) {
            return authData.getProviderData().get("email").toString();
        }
        return null;
    }
}
