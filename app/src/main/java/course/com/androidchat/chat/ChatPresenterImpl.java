package course.com.androidchat.chat;

import org.greenrobot.eventbus.Subscribe;

import course.com.androidchat.chat.ui.ChatView;
import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;

import static course.com.androidchat.entities.User.OFFLINE;
import static course.com.androidchat.entities.User.ONLINE;

public class ChatPresenterImpl implements ChatPresenter {
    private EventBus eventBus;
    private ChatView chatView;
    private ChatMessageInteractor chatMessageInteractor;
    private ChatSessionInteractor chatSessionInteractor;

    public ChatPresenterImpl(ChatView chatView) {
        this.chatView = chatView;
        eventBus = GreenRobotEventBus.getInstance();
        chatMessageInteractor = new ChatMessageInteractorImpl();
        chatSessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        chatMessageInteractor.unsubscribe();
        chatSessionInteractor.changeConnectionStatus(OFFLINE);
    }

    @Override
    public void onResume() {
        chatMessageInteractor.subscribe();
        chatSessionInteractor.changeConnectionStatus(ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatMessageInteractor.destroyListener();
        this.chatView = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatMessageInteractor.setChatRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatMessageInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (this.chatView != null) {
            this.chatView.onMessageReceived(event.getMessage());
        }
    }
}
