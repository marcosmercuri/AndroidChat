package course.com.androidchat.entities;

import com.google.firebase.database.Exclude;

public class ChatMessage {
    private String msg;
    private String sender;

    @Exclude
    private Boolean sentByMe;

    public ChatMessage() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Boolean getSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(Boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (msg != null ? !msg.equals(that.msg) : that.msg != null) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        return sentByMe != null ? sentByMe.equals(that.sentByMe) : that.sentByMe == null;

    }

    @Override
    public int hashCode() {
        int result = msg != null ? msg.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (sentByMe != null ? sentByMe.hashCode() : 0);
        return result;
    }
}
