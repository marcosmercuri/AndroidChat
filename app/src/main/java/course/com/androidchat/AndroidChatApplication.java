package course.com.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;

public class AndroidChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
