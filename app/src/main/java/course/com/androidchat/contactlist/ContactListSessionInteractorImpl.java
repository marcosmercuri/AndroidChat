package course.com.androidchat.contactlist;

public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {
    private ContactListRepository repository;

    public ContactListSessionInteractorImpl() {
        repository = new ContactListRepositoryImpl();
    }

    @Override
    public void signOff() {
        repository.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return repository.getCurrentUserEmail();
    }

    @Override
    public void changeConnectionStatus(Boolean online) {
        repository.changeConnectionStatus(online);
    }
}
