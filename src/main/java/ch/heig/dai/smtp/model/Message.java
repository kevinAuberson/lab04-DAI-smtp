package ch.heig.dai.smtp.model;

/**
 * Message class represents an email message.
 * It contains the sender, the recipients, the subject and the body of the message.
 * @author Kevin Auberson, Adrian Rogner
 */
public class Message {
    private String subject;
    private String body;

    /**
     * Constructor.
     *
     * @param subject The subject of the message.
     * @param body The body of the message.
     */
    public Message(String subject, String body){
        this.subject = subject;
        this.body = body;
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
