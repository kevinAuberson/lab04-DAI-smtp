package ch.heig.dai.smtp.network;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import java.util.Base64;
import ch.heig.dai.smtp.model.Group;

/**
 * SmtpClient class represents a simple SMTP client to send emails.
 * @author Kevin Auberson, Adrian Rogner
 */
public class SmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private static final String CONTENT_TYPE = "Content-Transfer-Encoding: base64\r\n\r\n";
    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;
    private Socket socket = null;
    private BufferedReader input = null;
    private BufferedWriter output = null;
    private final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * Constructor.
     *
     * @param ip The IP address of the SMTP server.
     * @param port The port of the SMTP server.
     */
    public SmtpClient(String ip, int port) {
        this.SERVER_ADDRESS = ip;
        this.SERVER_PORT = port;
    }

    /**
     * Opens a connection to the SMTP server.
     *
     * @throws IOException If an I/O error occurs while opening the connection.
     */
    private void openConnection() throws IOException {
        try {
            // Create a socket to connect to the server at the specified address and port.
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            // Set up input and output streams for communication with the server.
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
            LOG.info("Client: connection established");
        } catch (IOException e) {
            // Handle any exceptions that may occur during connection.
            System.out.println("Client: exc.: " + e);
        }
    }

    /**
     * Sends a command to the SMTP server.
     *
     * @param command The command to be sent.
     * @throws IOException If an I/O error occurs while sending the command.
     */
    private void sendRequest(String command) throws IOException {
        try {
            output.write(command + "\r\n");
            output.flush();
            LOG.info("Client: " + command);
        } catch(IOException e){
            // Handle any exceptions that may occur during write.
            System.out.println("Client: exc.: " + e);
        }
    }

    /**
     * Reads a response from the SMTP server.
     *
     * @throws IOException If an I/O error occurs while reading the response.
     */
    private void readRequest() throws IOException {
        try {
            String msg = input.readLine();
            LOG.info("Server: " + msg);
        } catch(IOException e){
            // Handle any exceptions that may occur during write.
            System.out.println("Client: exc.: " + e);
        }
    }

    /**
     * Reads the SMTP header.
     *
     * @throws IOException If an I/O error occurs while reading the header.
     */
    private void readHeaderSmtp() throws IOException{
        String msg;
        while ((msg = input.readLine()) != null) {
            LOG.info("Server: " + msg);
            if(!msg.startsWith("250")) {
                throw new IOException("Smtp erro: " + msg);
            } else if (msg.contains("250 SMTPUTF8")) {
                break;
            }
        }
    }

    private StringBuilder buildContent(String sender, List<String> recipient, String subject, String body) {
        StringBuilder content = new StringBuilder();
        content.append("From: ").append(sender).append("\r\n");
        content.append("To: ").append(String.join(",", recipient)).append("\r\n");
        content.append("Subject:=?utf-8?B?").append(encodeBase64(subject)).append("?=\r\n");
        content.append(CONTENT_TYPE);
        content.append(encodeBase64(body)).append("\r\n\r\n");
        content.append(".\r\n");
        return content;
    }

    /**
     * Encode une chaîne en base64.
     *
     * @param text La chaîne à encoder.
     * @return La chaîne encodée.
     */
    public String encodeBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(CHARSET));
    }
    /**
     * Sends an email using SMTP.
     *
     * @throws IOException If an I/O error occurs while sending the email.
     */
    public void sendEmail(List<Group> grp) throws IOException{

        for (Group g:grp) {
            openConnection();
            String info;

            readRequest();

            sendRequest("EHLO " + SERVER_ADDRESS);

            readHeaderSmtp();

            sendRequest("MAIL FROM: <" + g.getSender() + ">");

            readRequest();
            for (String r:g.getRecipients()) {
                sendRequest("RCPT TO: <" + r + ">");
            }

            readRequest();

            sendRequest("DATA");

            output.write(buildContent(g.getSender(), g.getRecipients(), g.getMessage().getSubject(), g.getMessage().getBody()).toString());
            output.flush();

            sendRequest("QUIT");

            input.close();
            output.close();
            socket.close();
        }

    }
}
