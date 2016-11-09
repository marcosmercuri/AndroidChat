package course.com.androidchat.contactlist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import course.com.androidchat.R;
import course.com.androidchat.contactlist.ContactListPresenter;
import course.com.androidchat.contactlist.ui.adapters.ContactListAdapter;
import course.com.androidchat.contactlist.ui.adapters.OnItemClickListener;
import course.com.androidchat.entities.User;
import course.com.androidchat.lib.GlideImageLoader;
import course.com.androidchat.lib.ImageLoader;

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

        setupComponents();
        presenter.onCreate();
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

    }


    @Override
    public void onContactAdded(User user) {

    }

    @Override
    public void onContactChanged(User user) {

    }

    @Override
    public void onContactRemoved(User user) {

    }

    @Override
    public void onItemClick(User user) {

    }

    @Override
    public void onItemLongClick(User user) {

    }
}
