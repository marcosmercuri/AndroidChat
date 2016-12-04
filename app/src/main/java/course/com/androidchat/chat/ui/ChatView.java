package course.com.androidchat.chat.ui;

import course.com.androidchat.entities.ChatMessage;

public interface ChatView {
    void onMessageReceived(ChatMessage message);

}
