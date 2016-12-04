package course.com.androidchat.chat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import course.com.androidchat.entities.ChatMessage;
import course.com.androidchat.helper.FirebaseHelper;
import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;

public class ChatRepositoryImpl implements ChatRepository {
    private String recipient;
    private FirebaseHelper firebaseHelper;
    private ChildEventListener childEventListener;
    private EventBus eventBus;

    public ChatRepositoryImpl() {
        this.firebaseHelper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();

    }

    @Override
    public void changeConnectionStatus(Boolean online) {
        firebaseHelper.changeUserConnectionStatus(online);
    }

    @Override
    public void setChatRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public void sendMessage(String msg) {
        DatabaseReference chatsReference = firebaseHelper.getAuthUserChatsReference(recipient);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMsg(msg);
        chatMessage.setSender(firebaseHelper.getAuthUserEmail());

        chatsReference.push().setValue(chatMessage);
    }

    @Override
    public void subscribe() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    chatMessage.setSentByMe(chatMessage.getSender().equals(firebaseHelper.getAuthUserEmail()));

                    ChatEvent chatEvent = new ChatEvent();
                    chatEvent.setMessage(chatMessage);
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            };
        }
        firebaseHelper.getAuthUserChatsReference(recipient).addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) {
            firebaseHelper.getAuthUserChatsReference(recipient).removeEventListener(childEventListener);
        }
    }

    @Override
    public void destroyListener() {
        childEventListener = null;
    }
}
