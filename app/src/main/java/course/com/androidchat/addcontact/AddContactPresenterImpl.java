package course.com.androidchat.addcontact;

import course.com.androidchat.addcontact.events.AddContactEvent;
import course.com.androidchat.addcontact.ui.AddContactView;

public class AddContactPresenterImpl implements AddContactPresenter {
    private AddContactView view;

    public AddContactPresenterImpl(AddContactView addContactView) {
        view = addContactView;
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void addContact(String email) {

    }

    @Override
    public void onEventMainThread(AddContactEvent event) {

    }
}
