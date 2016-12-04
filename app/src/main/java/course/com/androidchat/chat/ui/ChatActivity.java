package course.com.androidchat.chat.ui;

import static course.com.androidchat.entities.Constants.*;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import course.com.androidchat.R;
import course.com.androidchat.chat.ui.adapters.ChatAdapter;
import course.com.androidchat.chat.ChatPresenter;
import course.com.androidchat.chat.ChatPresenterImpl;
import course.com.androidchat.entities.ChatMessage;
import course.com.androidchat.lib.GlideImageLoader;
import course.com.androidchat.lib.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView {
    public static final String EMAIL_KEY = "email";
    public static final String ONLINE_KEY = "online";
    @Bind(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @Bind(R.id.editTxtMessage)
    EditText editTxtMessage;
    @Bind(R.id.btnSendMessage)
    ImageButton btnSendMessage;

    private ChatPresenter presenter;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();

        presenter = new ChatPresenterImpl(this);
        presenter.onCreate();
        setupToolbar(getIntent());
    }

    private void setupAdapter() {
        adapter = new ChatAdapter(this, new ArrayList<ChatMessage>());
    }

    private void setupRecyclerView() {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(adapter);
    }

    private void setupToolbar(Intent intent) {
        String recipient = intent.getStringExtra(EMAIL_KEY);
        presenter.setChatRecipient(recipient);

        Boolean online = intent.getBooleanExtra(ONLINE_KEY, false);
        String status;
        int textColor;
        if (online) {
            status = "online";
            textColor = Color.GREEN;
        } else {
            status = "offline";
            textColor = Color.RED;
        }
        txtStatus.setText(status);
        txtStatus.setTextColor(textColor);
        txtUser.setText(recipient);

        ImageLoader imageLoader = new GlideImageLoader(getApplicationContext());
        imageLoader.load(imgAvatar, DEFAULT_AVATAR_URL);
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(ChatMessage message) {
        adapter.add(message);
        messageRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
}
