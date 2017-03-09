
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Trivial client for the date server.
 */
public class client {

    /**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or hostname of a host running
     * the date server, then connects to it and displays the date that
     * it serves.
     */
    public static void main(String[] args) throws IOException {
        String cmd;
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 8080);
        BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Enter a command: ");
        cmd = scanner.nextLine();
        out.println(cmd);
        while(true){
          String input = in.readLine();
          if(in == null){
            break;
          }
          System.out.println(in.toString());
        }
        System.exit(0);
    }
}
