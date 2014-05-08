package sea_battle;

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

class Pole {

    private int pole[][] = new int[10][10];

    Pole() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.pole[i][j] = 0;
            }
        }
    }

    synchronized int[][] get() {
        return pole;
    }

    synchronized void set(int[][] a) {
        pole = a;
    }

}

class HttpConnect extends Thread {

    private Socket sock;
    private List<HttpConnect> ct;
    DataOutputStream pw = null;
    DataInputStream br = null;
    private String player = "first";
    private static Pole pole_1;
    private static Pole pole_2;
    private static Pole pole_3;
    private static Pole pole_4;

    HttpConnect(Socket socket, List<HttpConnect> ct) {
        this.sock = socket;
        this.ct = ct;
    }

    public void run() {

        try {
            pole_1 = new Pole();
            pole_2 = new Pole();
            pole_3 = new Pole();
            pole_4 = new Pole();
            String pl = "first";
            String req = null;
            int[][] temp = new int[10][10];
            int[][] temp2 = new int[10][10];
            int[][] temp3 = new int[10][10];
            int[][] temp4 = new int[10][10];
            int n = 0, t[][] = new int[10][10];
            br = new DataInputStream(sock.getInputStream());
            pw = new DataOutputStream(sock.getOutputStream());
            while (true) {
                req = br.readUTF();
                if (req.equals("exit")) {
                    break;
                }

                System.out.println("Request: " + req);

                if (req.equals("sendPl")) {
                    pl = br.readUTF();
                    for (HttpConnect client : ct) {
                        client.player = pl;
                    }
                }
                if (req.equals("getPl")) {
                    pl = player;
                    pw.writeUTF(pl);
                }

                if (req.equals("get_pole_1")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            pw.write(pole_1.get()[i][j]);
                        }
                    }

                }

                if (req.equals("get_pole_2")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            pw.write(pole_2.get()[i][j]);
                        }
                    }
                }

                if (req.equals("get_pole_3")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            pw.write(pole_3.get()[i][j]);
                        }
                    }
                }

                if (req.equals("get_pole_4")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            pw.write(pole_4.get()[i][j]);
                        }
                    }
                }

                if (req.equals("send_pole_1")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            temp[i][j] = br.read();
                        }
                    }
                    pole_1.set(temp);

                }

                if (req.equals("send_pole_2")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            temp2[i][j] = br.read();
                        }
                    }
                    pole_2.set(temp2);

                }

                if (req.equals("send_pole_3")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            temp3[i][j] = br.read();
                        }
                    }
                    pole_3.set(temp3);

                }

                if (req.equals("send_pole_4")) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            temp4[i][j] = br.read();
                        }
                    }
                    pole_4.set(temp4);

                }
                n++;
                System.out.println(n);
            }
            sock.close();

        } catch (IOException e) {

            System.out.println(e);

        }

    }

}
