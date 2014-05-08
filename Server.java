package sea_battle;

import sea_battle.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

    public static void main(String[] args) {
        try {

            ServerSocket ss = new ServerSocket(8081);
            List<HttpConnect> ct = new ArrayList<HttpConnect>();
            Socket socket = null;
            while (true) {
                socket = ss.accept();
                HttpConnect client = new HttpConnect(socket, ct);
                ct.add(client);
                client.start();
            }

        } catch (ArrayIndexOutOfBoundsException ae) {

            System.err.println("Usage: Server port");
            System.exit(0);
        } catch (IOException e) {

            System.out.println(e);
        }

    }

}

class HttpConnect extends Thread {

    private Socket sock;
    private List<HttpConnect> ct;
    DataOutputStream pw = null;
    DataInputStream br = null;

    HttpConnect(Socket socket, List<HttpConnect> ct) {
        this.sock = socket;
        this.ct = ct;
    }

    public void run() {

        try {
            String req = null;
            int[][] pole_1 = new int[10][10];
         //   int[][] pole_2 = new int[10][10];
         //   int[][] pole_3 = new int[10][10];
         //   int[][] pole_4 = new int[10][10];
            br = new DataInputStream(sock.getInputStream());
            pw = new DataOutputStream(sock.getOutputStream());
            while (true) {
                req = br.readUTF();
                if (req.equals("exit")) {
                    break;
                }

                System.out.println("Request: " + req);

                if (req.equals("get_pole")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            pw.write(pole_1[i][j]);
                        }
                    }

                }

                if (req.equals("send_pole")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            pole_1[i][j] = br.read();
                        }
                    }
                }
            }
            sock.close();

        } catch (IOException e) {

            System.out.println(e);

        }

    }
}
