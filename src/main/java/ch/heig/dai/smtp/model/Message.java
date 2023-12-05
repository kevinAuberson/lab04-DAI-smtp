package ch.heig.dai.smtp.model;

/**
 * Message class represents an email message.
 * It contains the sender, the recipients, the subject and the body of the message.
 * @author Kevin Auberson, Adrian Rogner
 */
public class Message {
    private String from;
    private String[] to;
    private String subject;
    private String body;

    /**
     * Constructor.
     *
     * @param from The sender of the message.
     * @param to The recipients of the message.
     * @param subject The subject of the message.
     * @param body The body of the message.
     */
    public Message(String from, String[] to, String subject, String body){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */
    public String getFrom(){
        return this.from;
    }

    /**
     * Returns the recipients of the message.
     *
     * @return The recipients of the message.
     */
    public String[] getTo(){
        return this.to;
    }

    /**
     * Returns the subject of the message.
     *
     * @return The subject of the message.
     */
    public String getSubject(){
        return this.subject;
    }

    /**
     * Returns the body of the message.
     *
     * @return The body of the message.
     */
    public String getBody(){
        return this.body;
    }
}
