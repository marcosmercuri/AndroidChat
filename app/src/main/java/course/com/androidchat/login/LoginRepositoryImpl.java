package course.com.androidchat.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import course.com.androidchat.entities.User;
import course.com.androidchat.helper.FirebaseHelper;
import course.com.androidchat.lib.EventBus;
import course.com.androidchat.lib.GreenRobotEventBus;
import course.com.androidchat.login.events.LoginEvent;

public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseHelper helper;
    private DatabaseReference authUserReference;

    public LoginRepositoryImpl(){
        helper = FirebaseHelper.getInstance();
        authUserReference = helper.getAuthUserReference();
    }

    @Override
    public void doSignUp(final String email, final String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(createSignUpOnSuccessListener(email, password))
                .addOnFailureListener(createOnFailureListener());
    }

    @NonNull
    private OnFailureListener createOnFailureListener() {
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(LoginEvent.ON_SIGN_IN_ERROR, e.getMessage());
            }
        };
    }

    @NonNull
    private OnSuccessListener<AuthResult> createSignUpOnSuccessListener(final String email, final String password) {
        return new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                postEvent(LoginEvent.ON_SIGN_UP_SUCCESS);
                doSignIn(email, password);
            }
        };
    }

    @Override
    public void doSignIn(String email, String password) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(createSignInOnSuccessListener())
                    .addOnFailureListener(createOnFailureListener());
        } catch (Exception e) {
            postEvent(LoginEvent.ON_SIGN_IN_ERROR, e.getMessage());
        }
    }

    @NonNull
    private OnSuccessListener<AuthResult> createSignInOnSuccessListener() {
        return new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                authUserReference = helper.getAuthUserReference();
                authUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        initSignIn(snapshot);
                    }
                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        postEvent(LoginEvent.ON_SIGN_IN_ERROR, firebaseError.getMessage());
                    }
                });
            }
        };
    }

    @Override
    public void checkSession() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            authUserReference = helper.getAuthUserReference();
            authUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    initSignIn(snapshot);
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    postEvent(LoginEvent.ON_SIGN_IN_ERROR, firebaseError.getMessage());
                }
            });
        } else {
            postEvent(LoginEvent.ON_RECOVER_SESSION_ERROR);
        }
    }

    private void registerNewUser() {
        String email = helper.getAuthUserEmail();
        if (email != null) {
            User currentUser = new User(email, true);
            authUserReference.setValue(currentUser);
        }
    }

    private void initSignIn(DataSnapshot snapshot){
        User currentUser = snapshot.getValue(User.class);

        if (currentUser == null) {
            registerNewUser();
        }
        helper.changeUserConnectionStatus(User.ONLINE);
        postEvent(LoginEvent.ON_SIGN_IN_SUCCESS);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

}
