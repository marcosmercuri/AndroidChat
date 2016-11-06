package course.com.androidchat.contactlist.ui.adapters;

import course.com.androidchat.entities.User;

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
