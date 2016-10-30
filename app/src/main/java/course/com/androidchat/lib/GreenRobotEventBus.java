package course.com.androidchat.lib;

public class GreenRobotEventBus implements EventBus {
    private de.greenrobot.event.EventBus eventBus;

    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }

    private GreenRobotEventBus() {
        eventBus = de.greenrobot.event.EventBus.getDefault();
    }

    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
