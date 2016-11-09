package course.com.androidchat.contactlist.repository;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import course.com.androidchat.contactlist.event.ContactListEvent;
import course.com.androidchat.entities.User;
import course.com.androidchat.helper.FirebaseHelper;
import course.com.androidchat.lib.GreenRobotEventBus;

class ContactListEventListener implements ChildEventListener {
    private final FirebaseHelper firebaseHelper;

    public ContactListEventListener() {
        this.firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        handleChange(dataSnapshot, ContactListEvent.ON_CONTACT_ADDED);
    }

    private void handleChange(DataSnapshot dataSnapshot, int eventType) {
        String email = firebaseHelper.convertEmailFromFirebaseFormat(dataSnapshot.getKey());
        Boolean online = (Boolean) dataSnapshot.getValue();
        User user = new User(email, online);
        post(eventType, user);
    }

    private void post(int eventType, User user) {
        ContactListEvent event = new ContactListEvent();
        event.setEventType(eventType);
        event.setUser(user);
        GreenRobotEventBus.getInstance().post(event);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        handleChange(dataSnapshot, ContactListEvent.ON_CONTACT_CHANGED);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        handleChange(dataSnapshot, ContactListEvent.ON_CONTACT_REMOVED);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        // Do nothing
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Do nothing
    }
}
