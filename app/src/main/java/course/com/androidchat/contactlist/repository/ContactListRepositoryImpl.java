package course.com.androidchat.contactlist.repository;

import com.google.firebase.database.ChildEventListener;

import course.com.androidchat.helper.FirebaseHelper;

public class ContactListRepositoryImpl implements ContactListRepository {
    private FirebaseHelper firebaseHelper;
    private ChildEventListener contactEventListener;

    public ContactListRepositoryImpl() {
        this.contactEventListener = new ContactListEventListener();
        this.firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void signOff() {
        firebaseHelper.notifyContactsOfSignOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebaseHelper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = firebaseHelper.getAuthUserEmail();
        firebaseHelper.getOneContactReference(currentUserEmail, email).removeValue();
        firebaseHelper.getOneContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void subscribeToContactListEvent() {
        if (contactEventListener==null) {
            contactEventListener = new ContactListEventListener();
        }
        firebaseHelper.getAuthUserContactsReference().addChildEventListener(contactEventListener);
    }

    @Override
    public void unsubscribeToContactListEvent() {
        if (contactEventListener!=null) {
            firebaseHelper.getAuthUserContactsReference().removeEventListener(contactEventListener);
        }
    }

    @Override
    public void destroyListener() {
        this.contactEventListener = null;
    }

    @Override
    public void changeConnectionStatus(Boolean online) {

    }
}
