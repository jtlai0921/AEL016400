package idv.ron.broadcastreceiverdemo;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Sms implements Serializable {
    private String sender;
    private String messages;
    private Date date;

    public Sms(String sender, String messages, Date date) {
        this.sender = sender;
        this.messages = messages;
        this.date = date;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = DateFormat.getInstance();
        String dateStr = dateFormat.format(date);
        String text = "sender: " + sender +
                "\nmessages: " + messages +
                "\ndate: " + dateStr;
        return text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
