package course.com.androidchat.addcontact;

public class AddContactInteractorImpl implements AddContactInteractor {
    private AddContactRepository repository;

    public AddContactInteractorImpl() {
        repository = new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        repository.addContact(email);
    }
}
