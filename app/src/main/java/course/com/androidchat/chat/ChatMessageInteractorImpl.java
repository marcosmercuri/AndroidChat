package course.com.androidchat.chat;

public class ChatMessageInteractorImpl implements ChatMessageInteractor {
    private ChatRepository chatRepository;

    public ChatMessageInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatRepository.setChatRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatRepository.sendMessage(msg);
    }

    @Override
    public void subscribe() {
        chatRepository.subscribe();
    }

    @Override
    public void unsubscribe() {
        chatRepository.unsubscribe();
    }

    @Override
    public void destroyListener() {
        chatRepository.destroyListener();
    }
}
