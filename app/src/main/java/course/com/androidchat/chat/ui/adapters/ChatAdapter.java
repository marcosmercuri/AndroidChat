package course.com.androidchat.chat.ui.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import course.com.androidchat.R;
import course.com.androidchat.entities.ChatMessage;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

   @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

        holder.setMessage(chatMessage, context);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void add(ChatMessage message) {
        if ( ! chatMessages.contains(message)) {
            chatMessages.add(message);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtMessage)
        TextView txtMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setMessage(ChatMessage message, Context context) {
            txtMessage.setText(message.getMsg());
            setTextVisualization(message, context);
        }

        public void setTextVisualization(ChatMessage chatMessage, Context context) {
            int color = fetchColor(R.attr.colorAccent, context);
            int gravity = Gravity.RIGHT;

            if (chatMessage.getSentByMe()) {
                color = fetchColor(R.attr.colorPrimary, context);
                gravity = Gravity.LEFT;
            }

            txtMessage.setBackgroundColor(color);

            LayoutParams params = (LayoutParams) txtMessage.getLayoutParams();
            params.gravity = gravity;
            txtMessage.setLayoutParams(params);
        }

        private int fetchColor(int color, Context context) {
            TypedValue typedValue = new TypedValue();
            TypedArray array = context.obtainStyledAttributes(typedValue.data, new int[] { color });
            int returnColor = array.getColor(0, 0);
            array.recycle();
            return returnColor;
        }
    }

}
