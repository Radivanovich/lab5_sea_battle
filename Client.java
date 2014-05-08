package sea_battle;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public Socket sock;

    public static void connection() {
        String host = "localhost";
        int port = 8080;
        try {
            Socket sock = new Socket(host, port);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void req(int ch) {
        PrintWriter pw;
        Client cl = new Client();
        try {
            pw = new PrintWriter(new OutputStreamWriter(cl.sock.getOutputStream()), true);
            pw.println("POST pole_1");
            BufferedReader br = new BufferedReader(new InputStreamReader(cl.sock.getInputStream()));
            String line = null;
            line = br.readLine();
            System.out.println(line);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        switch (ch) {
            case 1:
            case 2:
            case 3:
            case 4:
        }
    }

    public static void closeConnection() {
        {
            Client cl = new Client();
            try {
                cl.sock.close();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
