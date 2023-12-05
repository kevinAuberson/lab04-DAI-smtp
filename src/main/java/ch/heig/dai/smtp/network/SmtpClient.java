package ch.heig.dai.smtp.network;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 1025;
    private Socket socket = null;
    private BufferedReader input = null;
    private BufferedWriter output = null;
    private final Charset CHARSET = StandardCharsets.UTF_8;
    

    private void openConnection() throws IOException {
        try {
            // Create a socket to connect to the server at the specified address and port.
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            // Set up input and output streams for communication with the server.
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
        } catch (IOException e) {
            // Handle any exceptions that may occur during connection.
            System.out.println("Client: exc.: " + e);
        }
    }
    
    
    private void sendRequest(String command) throws IOException {
        try {
            output.write(command + "\n");
            output.flush();            
        } catch(IOException e){
            // Handle any exceptions that may occur during write.
            System.out.println("Client: exc.: " + e);
        }
    }
    private void readRequest() throws IOException {
        try {
            String msg = input.readLine();
            LOG.info(msg);
        } catch(IOException e){
            // Handle any exceptions that may occur during write.
            System.out.println("Client: exc.: " + e);
        }
    }

    private void readHeaderSmtp() throws IOException{
        String msg;
        while ((msg = input.readLine()) != null) {
            LOG.info(msg);
            if(!msg.startsWith("250")) {
                throw new IOException("Smtp erro: " + msg);
            } else if (msg.contains("250 SMTPUTF8")) {
                break;
            }
        }
    }

    public void sendEmail() throws IOException{
        LOG.info("Sending email with SMTP");
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
