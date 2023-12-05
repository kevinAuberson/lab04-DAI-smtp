package ch.heig.dai.smtp.network;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * SmtpClient class represents a simple SMTP client to send emails.
 * @author Kevin Auberson, Adrian Rogner
 */
public class SmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
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

    /**
     * Sends an email using SMTP.
     *
     * @throws IOException If an I/O error occurs while sending the email.
     */
    public void sendEmail() throws IOException{
        openConnection();
        String info;

        readRequest();

        sendRequest("EHLO localhost");

        readHeaderSmtp();

        sendRequest("MAIL FROM:");

        readRequest();

        sendRequest("RCPT TO:");

        readRequest();

        sendRequest("DATA");

        readRequest();

        sendRequest("QUIT");

        input.close();
        output.close();
        socket.close();
    }
}
