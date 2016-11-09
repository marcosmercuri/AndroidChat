package course.com.androidchat.contactlist;

import course.com.androidchat.contactlist.repository.ContactListRepository;
import course.com.androidchat.contactlist.repository.ContactListRepositoryImpl;

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
