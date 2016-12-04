package course.com.androidchat.chat;

public class ChatSessionInteractorImpl implements ChatSessionInteractor {
    private ChatRepository chatRepository;

    public ChatSessionInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }


    @Override
    public void changeConnectionStatus(Boolean online) {
        chatRepository.changeConnectionStatus(online);
    }
}
