package course.com.androidchat.chat;

public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();
    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}
