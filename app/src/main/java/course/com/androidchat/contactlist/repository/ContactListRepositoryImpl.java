package course.com.androidchat.contactlist.repository;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import course.com.androidchat.helper.FirebaseHelper;

public class ContactListRepositoryImpl implements ContactListRepository {
    private FirebaseHelper firebaseHelper;
    private ChildEventListener contactEventListener;

    public ContactListRepositoryImpl(ChildEventListener contactEventListener) {
        this.contactEventListener = contactEventListener;
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
            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            };
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
