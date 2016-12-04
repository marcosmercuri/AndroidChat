package course.com.androidchat.chat;

import course.com.androidchat.entities.ChatMessage;

public class ChatEvent {
    private ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
