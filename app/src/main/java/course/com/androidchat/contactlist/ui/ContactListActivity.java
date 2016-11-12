package course.com.androidchat.contactlist.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import course.com.androidchat.R;
import course.com.androidchat.addcontact.ui.AddContactFragment;
import course.com.androidchat.contactlist.ContactListPresenter;
import course.com.androidchat.contactlist.ContactListPresenterImpl;
import course.com.androidchat.contactlist.ui.adapters.ContactListAdapter;
import course.com.androidchat.contactlist.ui.adapters.OnItemClickListener;
import course.com.androidchat.entities.User;
import course.com.androidchat.lib.GlideImageLoader;
import course.com.androidchat.lib.ImageLoader;
import course.com.androidchat.login.ui.LoginActivity;

public class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener {
    private ContactListAdapter adapter;
    private ContactListPresenter presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        presenter = new ContactListPresenterImpl(this);
        presenter.onCreate();
        setupComponents();
        setupToolbar();
    }

    private void setupComponents() {
        ImageLoader loader = new GlideImageLoader(this.getApplicationContext());
        adapter = new ContactListAdapter(new ArrayList<User>(), loader, this);

        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    private void setupToolbar() {
        toolbar.setTitle(presenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @OnClick(R.id.fab)
    public void addContact() {
        new AddContactFragment().show(getSupportFragmentManager(), getString(R.string.addcontact_message_title));
    }

    @Override
    public void onContactAdded(User user) {
        adapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        adapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(User user) {
        presenter.removeContact(user.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        presenter.signOff();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
              | Intent.FLAG_ACTIVITY_NEW_TASK
              | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
