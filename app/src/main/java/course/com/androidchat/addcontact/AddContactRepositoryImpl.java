package course.com.androidchat.addcontact;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import course.com.androidchat.addcontact.events.AddContactEvent;
import course.com.androidchat.entities.User;
import course.com.androidchat.helper.FirebaseHelper;
import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;

public class AddContactRepositoryImpl implements AddContactRepository {
    private EventBus eventBus;
    private FirebaseHelper firebaseHelper;

    public AddContactRepositoryImpl() {
        eventBus = GreenRobotEventBus.getInstance();
        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void addContact(final String email) {
        DatabaseReference userReference = firebaseHelper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user!=null) {
                    DatabaseReference contactsReference = firebaseHelper.getAuthUserContactsReference();
                    final String key = firebaseHelper.convertEmailToFirebaseFormat(email);
                    contactsReference.child(key).setValue(user.getOnline());

                    String currentUserKey = firebaseHelper.convertEmailToFirebaseFormat(firebaseHelper.getAuthUserEmail());
                    DatabaseReference reverseContactReference = firebaseHelper.getContactsReference(email);
                    reverseContactReference.child(currentUserKey).setValue(User.ONLINE);

                    postSuccess();
                } else {
                    postError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                postError();
            }
        });
    }

    private void postSuccess() {
        post(false);
    }

    private void postError() {
        post(true);
    }

    private void post(boolean error) {
        AddContactEvent event = new AddContactEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
