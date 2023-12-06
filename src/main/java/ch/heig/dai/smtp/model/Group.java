package ch.heig.dai.smtp.model;

public class Group {
    private String sender;
    private String[] recipients;

    private Message message;

    public Group(String sender, String[] recipients, Message message){
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
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
    public String[] getRecipients(){
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
}