package course.com.androidchat.chat;

public interface ChatRepository {
    void changeConnectionStatus(Boolean online);

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void subscribe();
    void unsubscribe();
    void destroyListener();

}
