package ch.heig.dai.smtp.model;

import java.util.List;

public class Group {
    private String sender;
    private List<String> recipients;

    private Message message;

    public Group(String sender, List<String> recipients){
        this.sender = sender;
        this.recipients = recipients;
    }

    /**
     * Returns the sender of the group.
     *
     * @return The sender of the group.
     */
    public String getSender(){
        return this.sender;
    }

    /**
     * Returns the recipients of the group.
     *
     * @return The recipients of the group.
     */
    public List<String> getRecipients(){
        return this.recipients;
    }

    /**
     * Returns the message of the group.
     *
     * @return The message of the group.
     */
    public Message getMessage(){
        return this.message;
    }

    /**
     * Sets the message of the group.
     *
     * @param message The message of the group.
     */
    public void setMessage(Message message){
        this.message = message;
    }
}