package course.com.androidchat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class AndroidChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
    }

    private void initFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
