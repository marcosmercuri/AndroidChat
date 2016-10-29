package course.com.androidchat.helper;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

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

    public Firebase getAuthUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    public Firebase getUserReference(String email) {
        Firebase userReference = null;
        if (isNotBlank(email)) {
            String sanitizeEmail = sanitizeString(email);
            userReference = dataReference.getRoot().child(USERS_PATH).child(sanitizeEmail);
        }
        return userReference;
    }

    /*
     * Firebase doesn't allow dots as path
     */
    private String sanitizeString(String email) {
        return email.replace(".", "_");
    }

    private boolean isNotBlank(String email) {
        return "".equals(email);
    }

    public Firebase getContactsReference(String email) {
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public Firebase getAuthUserContactsReference() {
        return getContactsReference(getAuthUserEmail());
    }

    public Firebase getOneContactReference(String mainEmail, String childEmail) {
        String sanitizeChildEmail = sanitizeString(childEmail);
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(sanitizeChildEmail);
    }

    public Firebase getAuthUserChatsReference(String receiver) {
        String keySender = sanitizeString(getAuthUserEmail());
        String keyReceiver = sanitizeString(receiver);
        String keyChat = getKeyChat(keyReceiver, keySender);

        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

    private String getKeyChat(String keyReceiver, String keySender) {
        // The key must start with the lower letter
        if (keyReceiver.compareTo(keySender) < 1) {
            return keyReceiver + SEPARATOR + keySender;
        } else {
            return keySender + SEPARATOR + keyReceiver;
        }
    }

    public void changeUserConnectionStatus(Boolean isOnline) {
        Firebase userReference = getAuthUserReference();
        if (userReference != null) {
            Map<String, Object> updates = new HashMap<>(1);
            updates.put("online", isOnline);
            userReference.updateChildren(updates);
            notifyContactsOfConnectionChange(isOnline);
        }
    }

    public void notifyContactsOfConnectionChange(Boolean isOnline) {
        notifyContactsOfConnectionChange(isOnline, false);
    }

    public void notifyContactsOfSignOff() {
        notifyContactsOfConnectionChange(false, true);
    }

    private void notifyContactsOfConnectionChange(final Boolean isOnline, final Boolean signOff) {
        final String authUserEmail = getAuthUserEmail();
        // TODO Refactor
        getContactsReference(authUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String childEmail = child.getKey();
                    Firebase reference = getOneContactReference(childEmail, authUserEmail);
                    reference.setValue(isOnline);
                }
                if (signOff) {
                    dataReference.unauth();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }
}
