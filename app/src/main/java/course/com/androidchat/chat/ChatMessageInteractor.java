package course.com.androidchat.chat;

public interface ChatMessageInteractor {
    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void subscribe();
    void unsubscribe();
    void destroyListener();

}
