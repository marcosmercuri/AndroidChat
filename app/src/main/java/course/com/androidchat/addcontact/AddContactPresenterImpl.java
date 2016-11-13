package course.com.androidchat.addcontact;

import course.com.androidchat.addcontact.events.AddContactEvent;
import course.com.androidchat.addcontact.ui.AddContactView;
import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;
import org.greenrobot.eventbus.Subscribe;

public class AddContactPresenterImpl implements AddContactPresenter {
    private EventBus eventBus;
    private AddContactView view;
    private AddContactInteractor interactor;

    public AddContactPresenterImpl(AddContactView addContactView) {
        view = addContactView;
        eventBus = GreenRobotEventBus.getInstance();
        interactor = new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if (view != null) {
            view.hideInput();
            view.showProgressBar();
        }
        interactor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if (view!=null) {
            view.showInput();
            view.hideProgressBar();

            if (event.getError()) {
                view.contactNotAdded();
            } else {
                view.contactAdded();
            }
        }
    }
}
