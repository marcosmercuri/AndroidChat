package course.com.androidchat.contactlist;


import org.greenrobot.eventbus.Subscribe;

import course.com.androidchat.contactlist.event.ContactListEvent;
import course.com.androidchat.contactlist.ui.ContactListView;
import course.com.androidchat.entities.User;
import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;

import static course.com.androidchat.contactlist.event.ContactListEvent.ON_CONTACT_ADDED;
import static course.com.androidchat.contactlist.event.ContactListEvent.ON_CONTACT_CHANGED;
import static course.com.androidchat.contactlist.event.ContactListEvent.ON_CONTACT_REMOVED;
import static course.com.androidchat.entities.User.OFFLINE;
import static course.com.androidchat.entities.User.ONLINE;

public class ContactListPresenterImpl implements ContactListPresenter {
    private EventBus eventBus;
    private ContactListView view;
    private ContactListInteractor listInteractor;
    private ContactListSessionInteractor sessionInteractor;

    public ContactListPresenterImpl(ContactListView view) {
        this.view = view;
        eventBus = GreenRobotEventBus.getInstance();
        listInteractor = new ContactListInteractorImpl();
        sessionInteractor = new ContactListSessionInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        listInteractor.destroyListener();
        view = null;
    }

    @Override
    public void onPause() {
        sessionInteractor.changeConnectionStatus(OFFLINE);
        listInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        sessionInteractor.changeConnectionStatus(ONLINE);
        listInteractor.subscribe();
    }

    @Override
    public void signOff() {
        sessionInteractor.changeConnectionStatus(OFFLINE);
        listInteractor.unsubscribe();
        listInteractor.destroyListener();
        sessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return sessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        listInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        switch (event.getEventType()) {
            case ON_CONTACT_ADDED:
                onContactAdded(event.getUser());
                break;
            case ON_CONTACT_CHANGED:
                onContactChanged(event.getUser());
                break;
            case ON_CONTACT_REMOVED:
                onContactRemoved(event.getUser());
                break;
        }
    }

    private void onContactAdded(User user) {
        if (view!=null) {
            view.onContactAdded(user);
        }
    }

    private void onContactRemoved(User user) {
        if (view!=null) {
            view.onContactRemoved(user);
        }
    }

    private void onContactChanged(User user) {
        if (view!=null) {
            view.onContactChanged(user);
        }
    }
}
