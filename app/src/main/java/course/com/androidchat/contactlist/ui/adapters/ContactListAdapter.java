package course.com.androidchat.contactlist.ui.adapters;

import static course.com.androidchat.entities.Constants.DEFAULT_AVATAR_URL;

import java.util.List;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import course.com.androidchat.R;
import course.com.androidchat.entities.User;
import course.com.androidchat.lib.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public ContactListAdapter(List<User> contactList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = contactList.get(position);
        holder.setClickListener(user, onItemClickListener);
        holder.displayUserInfo(user);
        imageLoader.load(holder.imgAvatar, DEFAULT_AVATAR_URL);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtUser)
        TextView txtUser;
        @Bind(R.id.txtStatus)
        TextView txtStatus;
        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        private void setClickListener(final User user, final OnItemClickListener onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(user);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(user);
                    return false;
                }
            });
        }

        void displayUserInfo(User user) {
            String status;
            int textColor;
            if (user.getOnline()) {
                status = "online";
                textColor = Color.GREEN;
            } else {
                status = "offline";
                textColor = Color.RED;
            }
            txtStatus.setText(status);
            txtStatus.setTextColor(textColor);
            txtUser.setText(user.getEmail());
        }
    }

    public void add(User user) {
        if ( ! contactList.contains(user)) {
            contactList.add(user);
            notifyDataSetChanged();
        }
    }

    public void update(User user) {
        if (contactList.contains(user)) {
            int index = contactList.indexOf(user);
            contactList.set(index, user);
            notifyDataSetChanged();
        }
    }

    public void remove(User user) {
        if (contactList.contains(user)) {
            contactList.remove(user);
            notifyDataSetChanged();
        }
    }
}
