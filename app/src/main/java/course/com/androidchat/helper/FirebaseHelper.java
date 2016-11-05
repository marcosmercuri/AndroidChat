package course.com.androidchat.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import course.com.androidchat.entities.User;

public class FirebaseHelper {
    private final static String USERS_PATH = "users";
    private final static String CHATS_PATH = "chats";
    private final static String CONTACTS_PATH = "contacts";
    private final static String SEPARATOR = "___";
    private DatabaseReference dataReference;

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    private FirebaseHelper() {
        this.dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null) {
            return firebaseUser.getEmail();
        }
        return null;
    }

    public DatabaseReference getAuthUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getUserReference(String email) {
        DatabaseReference userReference = null;
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

    public DatabaseReference getContactsReference(String email) {
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getAuthUserContactsReference() {
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(String mainEmail, String childEmail) {
        String sanitizeChildEmail = sanitizeString(childEmail);
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(sanitizeChildEmail);
    }

    public DatabaseReference getAuthUserChatsReference(String receiver) {
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
        DatabaseReference userReference = getAuthUserReference();
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
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }

    private void notifyContactsOfConnectionChange(final Boolean isOnline, final Boolean signOff) {
        final String authUserEmail = getAuthUserEmail();
        // TODO Refactor
        getContactsReference(authUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String childEmail = child.getKey();
                    DatabaseReference reference = getOneContactReference(childEmail, authUserEmail);
                    reference.setValue(isOnline);
                }
                if (signOff) {
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
