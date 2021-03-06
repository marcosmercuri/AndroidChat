package course.com.androidchat.contactlist;

import course.com.androidchat.contactlist.repository.ContactListRepository;
import course.com.androidchat.contactlist.repository.ContactListRepositoryImpl;

public class ContactListInteractorImpl implements ContactListInteractor {
    private ContactListRepository repository;

    public ContactListInteractorImpl() {
        repository = new ContactListRepositoryImpl();
    }

    @Override
    public void subscribe() {
        repository.subscribeToContactListEvent();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribeToContactListEvent();
    }

    @Override
    public void removeContact(String email) {
        repository.removeContact(email);
    }

    @Override
    public void destroyListener() {
        repository.destroyListener();
    }
}
