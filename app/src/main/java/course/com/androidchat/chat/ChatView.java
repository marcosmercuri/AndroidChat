package course.com.androidchat.chat;

import course.com.androidchat.entities.ChatMessage;

public interface ChatView {
    void onMessageReceived(ChatMessage message);

}
